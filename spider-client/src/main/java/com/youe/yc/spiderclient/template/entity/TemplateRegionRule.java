package com.youe.yc.spiderclient.template.entity;

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
	private String selectExpression;
	private String urlRegex;

    /**
     * 区域的范围
	 */
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
