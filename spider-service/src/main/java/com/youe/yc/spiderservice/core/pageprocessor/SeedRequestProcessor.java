package com.youe.yc.spiderservice.core.pageprocessor;


import com.youe.yc.spiderclient.template.entity.TemplateRegionRule;
import com.youe.yc.spiderclient.template.enums.RegionRuleScope;
import com.youe.yc.spiderservice.core.request.TypeRequest;

/**
 * 种子请求处理器
 * @author ibm
 *
 */
public class SeedRequestProcessor extends AbstractRequestProcessor{

	@Override
	protected boolean isAvalibleToParse(TypeRequest request,TemplateRegionRule regionRule) {
		RegionRuleScope scope = RegionRuleScope.getByCode(regionRule.getScope());
		return RegionRuleScope.Seed == scope;
	}
	
	@Override
	public int getDeepth(TypeRequest request) {
		return 1;
	}
	
}
