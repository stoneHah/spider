package com.youe.yc.spiderclient.template.service;

import com.youe.yc.common.utils.JsonUtils;
import com.youe.yc.spiderclient.template.entity.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TemplateService {

    private static final String url = "http://localhost:8081/template/";

    @Autowired
    private RestTemplate restTemplate;

    public Template getTemplate(String id) {
        return restTemplate.getForObject(url + "{1}",Template.class,id);
    }

    public void upsertTemplate(Template template){
        String jsonStr = JsonUtils.toJsonString(template);
        restTemplate.postForObject(url + "save", template, Void.class);
    }

}
