package com.youe.yc.spiderservice.core.pipeline;

import com.youe.yc.spiderservice.core.entity.CrawlData;
import com.youe.yc.spiderservice.core.pageprocessor.RequestProcessor;
import com.youe.yc.spiderservice.core.persistent.IResultPersistentService;
import com.youe.yc.spiderservice.core.persistent.PersistentServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

@Component
public class GenericPipeline implements Pipeline {
	private static Logger logger = LoggerFactory.getLogger(GenericPipeline.class);

	@Autowired(required = false)
	private PersistentServiceFactory persistentServiceFactory;

	@Override
	public void process(ResultItems resultItems, Task task) {
		if (persistentServiceFactory == null) {
			logger.info("缺失PersistentServiceFactory的实现");
			return;
		}
		List<CrawlData> crawlDataList = resultItems.get(RequestProcessor.KEY_RESULT);
		List<IResultPersistentService> persistentServiceList = persistentServiceFactory.getPersistentServiceByDataType("");
		
		if(!CollectionUtils.isEmpty(persistentServiceList) && !CollectionUtils.isEmpty(crawlDataList)){
			for (IResultPersistentService persistentService : persistentServiceList) {
				for (CrawlData crawlData : crawlDataList) {
					logger.debug("开始持久化{}到{}", crawlData.toString(), persistentService.getDesc());
					
					if (!persistentService.saveCrawlerResult(crawlData)){
						logger.error("可能持久化{}到{}失败！", crawlData.toString(), persistentService.getDesc());
					}
				}
			}
		}
	}

}
