package com.youe.yc.spidertemplateservice;

import com.youe.yc.spidertemplateservice.entity.RequestUrl;
import com.youe.yc.spidertemplateservice.entity.Template;
import com.youe.yc.spidertemplateservice.entity.TemplateFiledRule;
import com.youe.yc.spidertemplateservice.entity.TemplateRegionRule;
import com.youe.yc.spidertemplateservice.enums.RegionRuleScope;
import com.youe.yc.spidertemplateservice.service.ITemplateService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpiderTemplateServiceApplicationTests {

	@Autowired
	private ITemplateService templateService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetTemplate(){
		Template template = templateService.getTemplate("0134345b-b784-4b08-a43f-0b459a95f684");
		assertNotNull(template);

		List<TemplateRegionRule> regionRules = template.getRegionRules();
		if(!CollectionUtils.isEmpty(regionRules)){
			for (TemplateRegionRule templateRegionRule : regionRules) {
				System.out.println(templateRegionRule);
			}
		}
	}

	@Test
	public void testSaveTemplate(){
		templateService.save(buildTemplate());
	}

	/**
	 *
	 * @return
	 */
	private Template buildTemplate() {
		Template template = new Template();
		template.setSeedUrl(new RequestUrl("https://www.cnblogs.com/"));
		template.setUrlRegex(new String[]{"http://www\\.cnblogs\\.com/.*/p/.*\\.html"});

		template.setRegionRules(buildRegionRules());
		return template;
	}

	private List<TemplateRegionRule> buildRegionRules() {
		TemplateRegionRule templateRegionRule = new TemplateRegionRule();
		templateRegionRule.setUrlRegex("http://www\\.cnblogs\\.com/.*/p/.*\\.html");
		templateRegionRule.setFieldRules(buildFieldRules());
		templateRegionRule.setScope(RegionRuleScope.Target.getCode());

		return Collections.singletonList(templateRegionRule);
	}

	private List<TemplateFiledRule> buildFieldRules() {
		TemplateFiledRule titleFieldRule = new TemplateFiledRule();
		titleFieldRule.setFieldName("title");
		titleFieldRule.setRemark("标题");
		titleFieldRule.setRule("//[@id=\"cb_post_title_url\"]/text()");

		TemplateFiledRule contentFieldRule = new TemplateFiledRule();
		contentFieldRule.setFieldName("content");
		contentFieldRule.setRemark("内容");
		contentFieldRule.setRule("//[@id=\"cnblogs_post_body\"]/html()");

		return Lists.newArrayList(titleFieldRule,contentFieldRule);
	}

}
