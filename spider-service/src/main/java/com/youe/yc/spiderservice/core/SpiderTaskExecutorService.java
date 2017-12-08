package com.youe.yc.spiderservice.core;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.youe.yc.spiderclient.template.entity.Template;
import com.youe.yc.spiderclient.template.service.TemplateService;
import com.youe.yc.spiderservice.core.exception.TaskExecuteException;
import com.youe.yc.spiderservice.core.request.RequestType;
import com.youe.yc.spiderservice.core.request.TypeRequest;
import com.youe.yc.spiderservice.core.spider.YoueSpider;
import com.youe.yc.spiderservice.core.task.ListableTaskExecutorService;
import com.youe.yc.spiderservice.core.task.Task;
import com.youe.yc.spiderservice.core.task.TaskExecutorContext;
import com.youe.yc.spiderservice.core.task.TaskInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.Scheduler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class SpiderTaskExecutorService implements ListableTaskExecutorService {

    private static final Logger logger = LoggerFactory.getLogger(SpiderTaskExecutorService.class);

	/*private static final BlockingQueue<TaskInfo> taskQueue = new ArrayBlockingQueue<>(50);*/

    private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            1, 16, 0, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(),
            new ThreadFactoryBuilder()
                    .setNameFormat("spider-task-executor-service")
                    .setDaemon(true)
                    .build());

    @Autowired
    private PageProcessor pageProcessor;

    @Autowired
    private Pipeline pipeline;

    @Autowired
    private TemplateService templateService;

    private final Scheduler scheduler = new QueueScheduler();

    /**
     * 存储模板与任务的关系
     */
    private final ConcurrentHashMap<String, Task> templateTaskMap = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<TaskInfo.TaskContext, TaskExecutorContext> taskContextMap = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<Task.TaskIdentifier, Task> taskIdToTaskMap = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<Task.TaskIdentifier, SpiderTask> taskIdToSpiderTaskMap = new ConcurrentHashMap();

    public SpiderTaskExecutorService() {
    }

    @Override
    public void execute(TaskInfo taskInfo) throws TaskExecuteException {
        checkUniqueTemplateTask(taskInfo.getTemplateId());

        Template template = getTemplate(taskInfo.getTemplateId());
        if (template == null) {
            throw new TaskExecuteException(String.format("please check whether the template[id=%s] is deleted", taskInfo.getTemplateId()));
        }

        Task task = createTask(taskInfo,template);

        runTask(task,template);
    }

    private void checkUniqueTemplateTask(String templateId) {
        if (templateTaskMap.containsKey(templateId)) {
            throw new TaskExecuteException(String.format("template[id=%s] has running task,please ensure only one task related with template", templateId));
        }
    }

    private Task createTask(TaskInfo taskInfo, Template template) {
        Task task = new Task(taskInfo);

        task.setName(template.getName() + task.getTaskIdentifier().toString());
        task.setSeedUrl(template.getSeedUrl().getUrl());

        Task previousTask = templateTaskMap.putIfAbsent(taskInfo.getTemplateId(), task);
        if (previousTask != null) {
            throw new TaskExecuteException(String.format("template[id=%s] has running task,please ensure only one task related with template", taskInfo.getTemplateId()));
        }

        taskIdToTaskMap.put(task.getTaskIdentifier(), task);

        TaskInfo.TaskContext taskContext = task.getTaskContext();
        if (taskContextMap.containsKey(taskContext)) {
            TaskExecutorContext taskExecutorContext = taskContextMap.get(taskContext);
            taskExecutorContext.addTask(task);
        } else {
            synchronized (taskContextMap) {
                TaskExecutorContext taskExecutorContext;
                if (taskContextMap.containsKey(taskContext)) {
                    taskExecutorContext = taskContextMap.get(taskContext);
                } else {
                    taskExecutorContext = new TaskExecutorContext();
                }

                taskExecutorContext.addTask(task);
                taskContextMap.put(taskContext, taskExecutorContext);
            }
        }

        return task;
    }

    private void runTask(Task task,Template template) {
        SpiderTask spiderTask = new SpiderTask(task, template);
        threadPool.execute(spiderTask);

        taskIdToSpiderTaskMap.put(task.getTaskIdentifier(), spiderTask);

        task.setTaskStatus(Task.TaskStatus.Running);
    }

    private Template getTemplate(String templateId) {
        return templateService.getTemplate(templateId);
    }

	/*private synchronized void checkTaskStatusAndUpdating(TaskInfo task, Task.TaskStatus curStatus, Task.TaskStatus expectStatus) {
		if (task.getTaskStatus() != curStatus) {
			throw new TaskExecuteException(String.format("任务的期望的当前状态为%s,但实际是%s", curStatus.toString(), task.getTaskStatus().toString()));
		}

		task.setTaskStatus(expectStatus);
	}*/

    @Override
    public boolean pause(Task.TaskIdentifier taskIdentifier) throws TaskExecuteException {
        return false;
    }

    @Override
    public boolean restart(Task.TaskIdentifier taskIdentifier) throws TaskExecuteException {
        return false;
    }

    @Override
    public boolean stop(Task.TaskIdentifier taskIdentifier) throws TaskExecuteException {
        return false;
    }

    @Override
    public long countTotalTasks() {
        return 0;
    }

    @Override
    public long countTotalTasks(TaskInfo.TaskContext taskContext) {
        return 0;
    }

    @Override
    public long countTasksByStatus(Task.TaskStatus taskStatus) {
        return 0;
    }

    @Override
    public Map<Task.TaskStatus, Long> countTasksGroupByStatus() {
        return null;
    }

    @Override
    public List<Task> getTasks(TaskInfo.TaskContext taskContext, Task.TaskStatus taskStatus) {
        TaskExecutorContext taskExecutorContext = taskContextMap.get(taskContext);
        return taskExecutorContext.getTasksByStatus(taskStatus);
    }

    @Override
    public Task getTask(Task.TaskIdentifier taskIdentifier) {
        return taskIdToTaskMap.get(taskIdentifier);
    }

    /**
     * 爬虫任务
     */
    private class SpiderTask implements Runnable {

        private YoueSpider youeSpider;

        public SpiderTask(Task task, Template template) {
            youeSpider = new YoueSpider(template, pageProcessor);
            youeSpider.uuid(task.getTaskIdentifier().getId()).completeCallback(new YoueSpider.CompleteCallback() {
                @Override
                public void apply() {
                    onTaskComplete(task);
                }
            }).addRequest(buildSeedRequest(task.getTaskIdentifier().getId(), template)).thread(task.getTaskInfo().getThreadNum())
                    //TODO 添加url管理组件
//				.setScheduler(scheduler)
                    .setPipelines(Collections.singletonList(pipeline));
        }

        @Override
        public void run() {
            youeSpider.run();
        }

        public void pause() {

        }

        public void resume() {

        }



        private Request buildSeedRequest(String taskId, Template template) {
            return new TypeRequest.TypeRequestBuilder(template.getSeedUrl().getUrl(), RequestType.Seed)
                    .depth(template.getDepth())
                    .taskId(taskId)
                    .templateId(template.getId())
                    .build();
        }

        protected void onTaskComplete(Task task) {
            task.setTaskStatus(Task.TaskStatus.Complete);
            task.setCompleteTime(new Date());

            templateTaskMap.remove(task.getTaskInfo().getTemplateId());

            logger.info("task completed and time consuming：{} ms", (task.getCompleteTime().getTime() - task.getCreateTime().getTime()));
        }

    }
}
