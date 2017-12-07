package com.youe.yc.spiderservice.core.pageprocessor;

import com.youe.yc.spiderclient.template.entity.TemplateRegionRule;
import com.youe.yc.spiderclient.template.enums.RegionRuleScope;
import com.youe.yc.spiderservice.core.request.TypeRequest;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * target请求处理器
 * @author ibm
 *
 */
public class TargetRequestProcessor extends AbstractRequestProcessor {

	@Override
	protected boolean isAvalibleToParse(TypeRequest request, TemplateRegionRule regionRule) {
		String urlRegex = regionRule.getUrlRegex();
		if(!StringUtils.hasText(urlRegex)){
			return false;
		}
		
		RegionRuleScope scope = RegionRuleScope.getByCode(regionRule.getScope());
		
		Pattern pattern = Pattern.compile(regionRule.getUrlRegex());
		if(pattern.matcher(request.getUrl()).matches()){
			return true;
		}
		
		return false;
	}
	
}
