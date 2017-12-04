package com.youe.yc.spiderservice.core.rule;

import org.springframework.util.StringUtils;

public class UrlParseRule {
	private String selectContext;
	
	private String urlPattern;

	public UrlParseRule(String selectContext, String urlPattern) {
		super();
		this.selectContext = selectContext;
		this.urlPattern = urlPattern;
	}

	public String getSelectContext() {
		return selectContext;
	}

	public void setSelectContext(String selectContext) {
		this.selectContext = selectContext;
	}

	public String getUrlPattern() {
		return urlPattern;
	}

	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}
	
	public boolean isValid(){
		return !StringUtils.hasText(selectContext) && 
				!StringUtils.hasText(urlPattern);
	}
	
}
