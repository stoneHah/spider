package com.youe.yc.spiderclient.spidertask.service;

import com.youe.yc.common.vo.Response;
import com.youe.yc.spiderclient.spidertask.entity.CrawlData;
import com.youe.yc.spiderclient.spidertask.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class SpiderTaskService {

    private static final String url = "http://localhost:8083/spider/";

    @Autowired
    private RestTemplate restTemplate;

    public Response<Void> executeTemplate(String templateId) {
        return restTemplate.postForObject(url + "execute/{1}", null, Response.class, templateId);
    }

    public Response<List<Task>> getTasks(Task.TaskStatus taskStatus){
        return restTemplate.getForObject(url + "tasks/{1}", Response.class,taskStatus.getCode());
    }

    public Response<List<CrawlData>> getCrawlData(String taskId){
        return restTemplate.getForObject(url + "crawData/{1}", Response.class,taskId);
    }
}
