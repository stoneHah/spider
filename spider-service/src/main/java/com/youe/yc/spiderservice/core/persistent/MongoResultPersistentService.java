package com.youe.yc.spiderservice.core.persistent;

import com.youe.yc.common.utils.IdGenerator;
import com.youe.yc.common.utils.JsonUtils;
import com.youe.yc.spiderclient.template.entity.Template;
import com.youe.yc.spiderclient.template.service.TemplateService;
import com.youe.yc.spiderservice.core.entity.CrawlData;
import com.youe.yc.spiderservice.util.DBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class MongoResultPersistentService implements IResultPersistentService {

	private static Logger logger = LoggerFactory.getLogger(MongoResultPersistentService.class);

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private TemplateService templateService;

	@Override
	public boolean saveCrawlerResult(CrawlData crawlData) {
		Template template = templateService.getTemplate(crawlData.getTemplateId());
		if(template == null){
			logger.error("爬虫模板{}已被删除",crawlData.getTemplateId());
			return false;
		}
		
		crawlData.setId(IdGenerator.getId());
		
		try {
			String collectionName = DBUtils.determineMongoCollection(template.getSeedUrl().getUrl());
			JsonUtils.toJsonString(crawlData);
			mongoTemplate.save(crawlData, collectionName);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return false;
	}

	@Override
	public boolean isSupportedByDataType(String dataType) {
		return true;
	}

	@Override
	public String getDesc() {
		return "Mongodb";
	}

}
