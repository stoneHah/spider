package com.youe.yc.spidertemplateservice.entity;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author qun.zheng
 * @since 2017-11-23
 */
public class TemplateRegionRule {

	private String name;
	@Field("select_expression")
	private String selectExpression;
	@Field("url_regex")
	private String urlRegex;

	/**
	 * 区域的范围
	 */
	@Field("scope")
	private Integer scope;
	
	/**
	 * 字段规则
	 */
	private List<TemplateFiledRule> fieldRules;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSelectExpression() {
		return selectExpression;
	}

	public void setSelectExpression(String selectExpression) {
		this.selectExpression = selectExpression;
	}

	public String getUrlRegex() {
		return urlRegex;
	}

	public void setUrlRegex(String urlRegex) {
		this.urlRegex = urlRegex;
	}

	public List<TemplateFiledRule> getFieldRules() {
		return fieldRules;
	}

	public void setFieldRules(List<TemplateFiledRule> fieldRules) {
		this.fieldRules = fieldRules;
	}
	
	public Integer getScope() {
		return scope;
	}

	public void setScope(Integer scope) {
		this.scope = scope;
	}

	@Override
	public String toString() {
		return "TemplateRegionRule{" +
			", name=" + name +
			", selectExpression=" + selectExpression +
			", urlRegex=" + urlRegex +
			"}";
	}
}
