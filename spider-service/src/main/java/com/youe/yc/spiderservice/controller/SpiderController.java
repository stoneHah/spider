package com.youe.yc.spiderservice.controller;

import com.youe.yc.common.vo.Response;
import com.youe.yc.spiderclient.template.service.TemplateService;
import com.youe.yc.spiderservice.core.entity.CrawlData;
import com.youe.yc.spiderservice.core.task.ListableTaskExecutorService;
import com.youe.yc.spiderservice.core.task.Task;
import com.youe.yc.spiderservice.core.task.TaskInfo;
import com.youe.yc.spiderservice.util.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/spider")
public class SpiderController {

    @Autowired
    private ListableTaskExecutorService taskExecutorService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "/execute/{templateId}", method = RequestMethod.POST)
    public Response<Void> executeTask(@PathVariable("templateId") String templateId) {
        Response.ResponseBuilder builder = new Response.ResponseBuilder(null)
                .success(true);
        try {
            taskExecutorService.execute(buildTaskInfo(templateId));
        } catch (Exception e) {
            builder.success(false)
                    .errorMsg(e.getMessage());
        }
        return builder.build();
    }

    @RequestMapping(value = "/tasks/{taskStatus}", method = RequestMethod.GET)
    public Response<List<Task>> getTasks(@PathVariable("taskStatus") int taskStatus){
        Response.ResponseBuilder builder = new Response.ResponseBuilder()
                .success(true);

        try {
            List<Task> tasks = taskExecutorService.getTasks(TaskInfo.TaskContext.GLOBAL_CONTEXT, Task.TaskStatus.getByCode(taskStatus));
            builder.data(tasks);
        } catch (Exception e) {
            e.printStackTrace();

            builder.success(false).errorMsg(e.getMessage());
        }

        return builder.build();
    }

    @RequestMapping(value = "/crawData/{taskId}",method = RequestMethod.GET)
    public Response<List<CrawlData>> getCrawlData(@PathVariable("taskId") String taskId){
        Response.ResponseBuilder builder = new Response.ResponseBuilder()
                .success(true);

        Task task = taskExecutorService.getTask(new Task.TaskIdentifier(taskId));
        if (task == null) {
            builder.success(false).errorMsg("未找到此任务");
        }else{
            String collection = DBUtils.determineMongoCollection(task.getSeedUrl());

            Criteria criteria = Criteria.where("taskId").is(taskId);
            List<CrawlData> dataList = mongoTemplate.find(new Query(criteria).with(new PageRequest(0, 10, new Sort(Sort.Direction.ASC, "date"))),
                    CrawlData.class, collection);

            builder.data(dataList);
        }

        return builder.build();
    }

    private TaskInfo buildTaskInfo(String templateId) {
        TaskInfo taskInfo = new TaskInfo(templateId);

        return taskInfo;
    }
}
