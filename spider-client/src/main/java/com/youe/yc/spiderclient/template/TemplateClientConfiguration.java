package com.youe.yc.spiderclient.template;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackages="com.youe.yc.spiderclient.template")
public class TemplateClientConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = RestTemplate.class)
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
