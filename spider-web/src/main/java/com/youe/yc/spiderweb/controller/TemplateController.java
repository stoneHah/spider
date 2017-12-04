package com.youe.yc.spiderweb.controller;

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

    @Autowired
    private TemplateService templateService;

    @RequestMapping(method = RequestMethod.GET)
    public String home() {
        return "home";
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

}
