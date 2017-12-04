package com.youe.yc.spiderservice;

import com.youe.yc.spiderclient.EnableTemplateClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableTemplateClient
public class SpiderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpiderServiceApplication.class, args);
	}
}
