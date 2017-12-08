package com.youe.yc.spiderclient.spidertask.entity;

import java.util.Date;

public class CrawlData {
	private String id;
	
	private String taskId;
	private String templateId;
	private String pageUrl;
	
	private RegionData regionData;
	private Date date;

	public CrawlData() {
		this.date = new Date();
	}

	public CrawlData(String taskId, RegionData regionData) {
		super();
		this.taskId = taskId;
		this.regionData = regionData;
		
		this.date = new Date();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public RegionData getRegionData() {
		return regionData;
	}

	public void setRegionData(RegionData regionData) {
		this.regionData = regionData;
	}
	
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	
	public Date getDate() {
		return date;
	}

	@Override
	public String toString() {
		return new StringBuilder()
			.append("[taskid="+taskId + " ,")
			.append(" regionData=" + regionData.toString())
			.append("]").toString();
	}

}
