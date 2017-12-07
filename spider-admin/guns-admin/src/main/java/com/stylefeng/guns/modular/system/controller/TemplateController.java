package com.stylefeng.guns.modular.system.controller;

import com.youe.yc.common.utils.JsonUtils;
import com.youe.yc.common.vo.Response;
import com.youe.yc.spiderclient.template.entity.Template;
import com.youe.yc.spiderclient.template.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    public Response upsertTemplate(@RequestBody String templateJsonStr){
        Response.ResponseBuilder<Void> builder = new Response.ResponseBuilder<Void>(null)
                .success(true);
        try {
            if (!StringUtils.hasText(templateJsonStr)) {
                throw new IllegalArgumentException("配置信息不能为空");
            }

            Template template = JsonUtils.parseObject(templateJsonStr, Template.class);
            templateService.upsertTemplate(template);
        } catch (Exception e) {
            builder.success(false)
                    .errorMsg(e.getMessage());
        }

        return builder.build();
    }

}
