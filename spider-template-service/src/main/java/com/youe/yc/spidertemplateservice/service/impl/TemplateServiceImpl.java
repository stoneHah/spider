package com.youe.yc.spidertemplateservice.service.impl;

import com.youe.yc.spidertemplateservice.entity.Template;
import com.youe.yc.spidertemplateservice.service.ITemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class TemplateServiceImpl implements ITemplateService {
	
	private static final Logger logger = LoggerFactory.getLogger(TemplateServiceImpl.class);

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Template getTemplate(String id) {
		return mongoTemplate.findById(id,Template.class);
	}

	@Override
	public void save(Template template) {
		if(!StringUtils.hasText(template.getId())){
			template.setCreateTime(new Date());
		}
		template.setUpdateTime(new Date());
		mongoTemplate.save(template);
	}

	@Override
	public List<Template> findAll() {
		return mongoTemplate.findAll(Template.class);
	}

	/*@Override
	public void update(Template template) {
		Assert.hasText(template.getId(),"模板的id不能为空");
		mongoTemplate.save(template);
	}*/
}
