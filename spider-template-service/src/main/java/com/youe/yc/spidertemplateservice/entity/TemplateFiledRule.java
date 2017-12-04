package com.youe.yc.spidertemplateservice.entity;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * <p>
 * 
 * </p>
 *
 * @author qun.zheng
 * @since 2017-11-23
 */
public class TemplateFiledRule {

	@Field("field_name")
	private String fieldName;
	private String remark;
	private String rule;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	@Override
	public String toString() {
		return "TemplateFiledRule{" +
			", fieldName=" + fieldName +
			", remark=" + remark +
			", rule=" + rule +
			"}";
	}
}
