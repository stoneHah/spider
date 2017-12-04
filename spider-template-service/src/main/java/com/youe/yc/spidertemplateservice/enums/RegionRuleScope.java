package com.youe.yc.spidertemplateservice.enums;

/**
 * 
 * @author ibm
 *
 */
public enum RegionRuleScope {
	Seed(0),SecondSeed(1),Target(2);
	
	private int code;

	private RegionRuleScope(int code) {
		this.code = code;
	}
	
	public int getCode(){
		return code;
	}
	
	public static RegionRuleScope getByCode(Integer code) {
		if(code == null){
			return null;
		}
		
		RegionRuleScope[] values = values();
		for (RegionRuleScope s : values) {
			if(s.code == code) {
				return s;
			}
		}
		
		return null;
	}
}
