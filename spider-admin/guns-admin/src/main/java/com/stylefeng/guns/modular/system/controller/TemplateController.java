package com.stylefeng.guns.modular.system.controller;

import com.youe.yc.common.vo.Response;
import com.youe.yc.spiderclient.template.entity.Template;
import com.youe.yc.spiderclient.template.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/template")
public class TemplateController {

    private static String PREFIX = "/system/template/";

    @Autowired
    private TemplateService templateService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public String home() {
        return PREFIX + "/template.html";
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<Template> list(){
        return templateService.findAll();
    }

    @RequestMapping("/{templateId}")
    @ResponseBody
    public Template getTemplate(@PathVariable("templateId") String templateId){
        return templateService.getTemplate(templateId);
    }

    @RequestMapping("/upsert")
    @ResponseBody
    public Response upsertTemplate(Template template){
        Response.ResponseBuilder<Void> builder = new Response.ResponseBuilder<Void>(null);
        try {
            templateService.upsertTemplate(template);
        } catch (Exception e) {
            builder.success(false)
                    .errorMsg(e.getMessage());
        }

        return builder.build();
    }

}
