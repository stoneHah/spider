package com.youe.yc.spiderclient;

import com.youe.yc.spiderclient.spidertask.SpiderTaskClientConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Configuration
@Import(SpiderTaskClientConfiguration.class)
public @interface EnableSpiderTaskClient {
}
