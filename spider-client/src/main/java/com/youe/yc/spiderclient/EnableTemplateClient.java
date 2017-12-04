package com.youe.yc.spiderclient;

import com.youe.yc.spiderclient.template.TemplateClientConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Configuration
@Import(TemplateClientConfiguration.class)
public @interface EnableTemplateClient {
}
