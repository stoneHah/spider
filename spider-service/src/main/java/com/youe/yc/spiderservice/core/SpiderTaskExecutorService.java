package com.youe.yc.spiderservice.core;

import com.youe.yc.spiderclient.template.entity.Template;
import com.youe.yc.spiderclient.template.service.TemplateService;
import com.youe.yc.spiderservice.core.exception.TaskExecuteException;
import com.youe.yc.spiderservice.core.request.RequestType;
import com.youe.yc.spiderservice.core.request.TypeRequest;
import com.youe.yc.spiderservice.core.spider.YoueSpider;
import com.youe.yc.spiderservice.core.task.Task;
import com.youe.yc.spiderservice.core.task.TaskExecutorContext;
import com.youe.yc.spiderservice.core.task.TaskExecutorService;
import com.youe.yc.spiderservice.core.task.TaskList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.Scheduler;

import java.util.Collections;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SpiderTaskExecutorService implements TaskExecutorService {
	
	/*private static final BlockingQueue<Task> taskQueue = new ArrayBlockingQueue<>(50);
	
	private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            1, 16, 0, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(),
            new ThreadFactoryBuilder()
                    .setNameFormat("spider-task-executor-service")
                    .setDaemon(true)
                    .build());*/
	
	@Autowired
	private PageProcessor pageProcessor;
	
	@Autowired
	private Pipeline pipeline;
	
	@Autowired
	private TemplateService templateService;
	
	private Scheduler scheduler = new QueueScheduler();
	
	private ConcurrentHashMap<TaskExecutorContext, TaskList> taskExecutorContextMap = new ConcurrentHashMap<>(8);
	
	private ConcurrentHashMap<Task, YoueSpider> taskSpiderMap = new ConcurrentHashMap();
	
	public SpiderTaskExecutorService(){
//		startTaskExecutor();
	}
	
	private void startTaskExecutor() {
		
	}

	@Override
	public void execute(Task task, TaskExecutorContext executorContext) throws TaskExecuteException {
		checkTaskStatusAndUpdating(task, Task.TaskStatus.Create, Task.TaskStatus.Running);
		
		addTask(task,executorContext);
		
		runTask(task);
	}
	
	private void runTask(Task task) {
		Template template = getTemplate(task);
		if(template == null){
			task.setTaskStatus(Task.TaskStatus.Exception);
			return;
		}
		
		YoueSpider youeSpider = new YoueSpider(template, pageProcessor);
		youeSpider.uuid(task.getTaskId()).completeCallback(new YoueSpider.CompleteCallback() {
			@Override
			public void apply() {
				onTaskComplete(task);
			}
		}).addRequest(buildSeedRequest(task.getTaskId(),template)).thread(5)
		.setScheduler(scheduler)
		.setPipelines(Collections.singletonList(pipeline))
		.runAsync();
		
		taskSpiderMap.put(task, youeSpider);
	}

	protected void onTaskComplete(Task task) {
		task.setTaskStatus(Task.TaskStatus.Complete);
		task.setCompleteTime(new Date());
		
		//移除task与spider的对应关系
		taskSpiderMap.remove(task);
		
		System.out.println("任务执行时长：" + (task.getCompleteTime().getTime() - task.getCreateTime().getTime()) +  " ms");
	}

	private Request buildSeedRequest(String taskId,Template template) {
		return new TypeRequest.TypeRequestBuilder(template.getSeedUrl().getUrl(), RequestType.Seed)
				.depth(template.getDepth())
				.taskId(taskId)
				.templateId(template.getId())
				.build();
	}

	private Template getTemplate(Task task) {
		return templateService.getTemplate(task.getTemplateId());
	}

	private void addTask(Task task, TaskExecutorContext executorContext) {
		taskExecutorContextMap.putIfAbsent(executorContext, new TaskList());
		
		TaskList taskList = taskExecutorContextMap.get(executorContext);
		taskList.addTask(task);
	}

	private synchronized void checkTaskStatusAndUpdating(Task task, Task.TaskStatus curStatus, Task.TaskStatus expectStatus) {
		if(task.getTaskStatus() != curStatus){
			throw new TaskExecuteException(String.format("任务的期望的当前状态为%s,但实际是%s", curStatus.toString(),task.getTaskStatus().toString()));
		}
		
		task.setTaskStatus(expectStatus);
    }

	@Override
	public boolean pause(Task task, TaskExecutorContext executorContext) throws TaskExecuteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean restart(Task task, TaskExecutorContext executorContext) throws TaskExecuteException  {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean stop(Task task, TaskExecutorContext executorContext) throws TaskExecuteException  {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int countTotalTasks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countTotalTasks(TaskExecutorContext executorContext) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countTaskByStatus(Task.TaskStatus taskStatus) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countTaskByStatus(TaskExecutorContext executorContext, Task.TaskStatus taskStatus) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countExecutorService() {
		// TODO Auto-generated method stub
		return 0;
	}

}
