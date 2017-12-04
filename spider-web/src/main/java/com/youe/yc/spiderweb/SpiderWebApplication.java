package com.youe.yc.spiderweb;

import com.youe.yc.spiderclient.EnableTemplateClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableTemplateClient
public class SpiderWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpiderWebApplication.class, args);
	}
}
