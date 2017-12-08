package com.stylefeng.guns.modular.system.controller;

import com.youe.yc.common.vo.Response;
import com.youe.yc.spiderclient.spidertask.entity.CrawlData;
import com.youe.yc.spiderclient.spidertask.entity.Task;
import com.youe.yc.spiderclient.spidertask.service.SpiderTaskService;
import com.youe.yc.spiderclient.template.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/spiderTask")
public class SpiderTaskController {

    private static String PREFIX = "/system/spiderTask/";

    @Autowired
    private SpiderTaskService spiderTaskService;

    @Autowired
    private TemplateService templateService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home() {
        return PREFIX + "/task_list.html";
    }

    @RequestMapping(value = "/execute/{templateId}", method = RequestMethod.POST)
    @ResponseBody
    public Response<Void> executeTask(@PathVariable("templateId") String templateId) {
        return spiderTaskService.executeTemplate(templateId);
    }

    @RequestMapping("/list/{taskStatus}")
    @ResponseBody
    public List<Task> list(@PathVariable("taskStatus") int taskStatus) {
        Response<List<Task>> res = spiderTaskService.getTasks(Task.TaskStatus.getByCode(taskStatus));
        if (res.isSuccess()) {
            return res.getData();
        }

        return null;
    }

    @RequestMapping(value = "/viewData/{taskId}",method = RequestMethod.GET)
    @ResponseBody
    public Response<List<CrawlData>> getCrawlData(@PathVariable("taskId") String taskId){
        return spiderTaskService.getCrawlData(taskId);
    }
}
