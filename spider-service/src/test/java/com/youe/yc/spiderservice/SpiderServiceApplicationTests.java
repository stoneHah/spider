package com.youe.yc.spiderservice;

import com.youe.yc.spiderclient.EnableTemplateClient;
import com.youe.yc.spiderservice.core.task.Task;
import com.youe.yc.spiderservice.core.task.TaskExecutorContext;
import com.youe.yc.spiderservice.core.task.TaskExecutorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableTemplateClient
public class SpiderServiceApplicationTests {

	@Autowired
	private TaskExecutorService taskExecutorService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testTaskExecutorService() throws Exception{
		Task task = new Task("5a211b8eae6ee211640fb64e");

		taskExecutorService.execute(task, new TaskExecutorContext("123"));

		Thread.currentThread().sleep(600000);
	}

}
