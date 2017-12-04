package com.youe.yc.spiderservice.core.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Map;

/**
 * 区域数据
 * 
 * @author ibm
 *
 */
public class RegionData {
	private String regionName;
	private Map<String, Object> dataMap;

	public RegionData(String regionName, Map<String, Object> dataMap) {
		super();
		this.regionName = regionName;
		this.dataMap = dataMap;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
