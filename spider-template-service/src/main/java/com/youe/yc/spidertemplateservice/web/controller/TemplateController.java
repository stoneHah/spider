package com.youe.yc.spidertemplateservice.web.controller;

import com.youe.yc.common.utils.JsonUtils;
import com.youe.yc.spidertemplateservice.entity.Template;
import com.youe.yc.spidertemplateservice.service.ITemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    public ITemplateService service;

    @RequestMapping(value = "/{templateId}", method = RequestMethod.GET)
    public Template getTemplate(@PathVariable("templateId") String tempId) {
        return service.getTemplate(tempId);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void saveTemplate(@RequestBody String templateJson) {
        if (!StringUtils.hasText(templateJson)) {
            return;
        }

        Template template = JsonUtils.parseObject(templateJson, Template.class);
        service.save(template);
    }

    /*@RequestMapping(value = "/update", method = RequestMethod.POST)
    public void updateTemplate(@RequestParam("templateId")String templateId,@RequestParam("template") String templateJson){
        if (!StringUtils.hasText(templateJson) || !StringUtils.hasText(templateId)) {
            return;
        }

        Template template = JsonUtils.parseObject(templateJson, Template.class);
        template.setId(templateId);
        service.update(template);
    }*/
}
