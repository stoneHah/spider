package com.youe.yc.spiderservice;

import com.youe.yc.spiderclient.EnableTemplateClient;
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
		/*TaskInfo task = new TaskInfo("5a2761d3ae6ee226b05d2348");

		taskExecutorService.execute(task, new TaskExecutorContext("123"));

		Thread.currentThread().sleep(600000);*/
	}

}
