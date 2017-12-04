package com.youe.yc.spiderclient;

import com.youe.yc.common.utils.JsonUtils;
import com.youe.yc.spiderclient.template.entity.Template;
import com.youe.yc.spiderclient.template.service.TemplateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpiderClientApplicationTests {

	@Autowired
	private TemplateService templateService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetTemplate(){
		Template template = templateService.getTemplate("5a211b8eae6ee211640fb64e");
		System.out.println(JsonUtils.toJsonString(template));
	}

}
