package com.youe.yc.spiderservice.core.pageprocessor;

import com.youe.yc.spiderclient.template.entity.Template;
import com.youe.yc.spiderclient.template.entity.TemplateFiledRule;
import com.youe.yc.spiderclient.template.entity.TemplateRegionRule;
import com.youe.yc.spiderservice.core.entity.CrawlData;
import com.youe.yc.spiderservice.core.entity.RegionData;
import com.youe.yc.spiderservice.core.request.TypeRequest;
import com.youe.yc.spiderservice.core.resolver.RequestResolver;
import com.youe.yc.spiderservice.core.resolver.SelectorExpressionResolver;
import com.youe.yc.spiderservice.util.DataProcessor;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Json;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 默认请求处理器
 * @author ibm
 *
 */
public abstract class AbstractRequestProcessor implements RequestProcessor{

	@Override
	public void process(Page page, TypeRequest request) {
		Template template = getTemplate(request);
		if(template == null){
			return;
		}
		
		parseTargetRequest(page,request,template.getUrlRegex(),template.getDepth());
		
		parseData(page,request,template);
	}
	
	/**
	 * 解析目标url
	 * @param page
	 * @param urlRegex
	 */
	private void parseTargetRequest(Page page, TypeRequest request, String[] urlRegex, int maxDeepth) {
		int curDeepth = getDeepth(request);
		if(curDeepth >= maxDeepth){
			return;
		}
		
		List<TypeRequest> requestList = RequestResolver.parseRequest(page.getHtml(), urlRegex,new Function<TypeRequest.TypeRequestBuilder, Void>() {
			@Override
			public Void apply(TypeRequest.TypeRequestBuilder builder) {
				builder.depth(curDeepth + 1)
						.taskId(request.getTaskId())
						.template(request.getTemplateData());
				return null;
			}
		});
		
		if(!CollectionUtils.isEmpty(requestList)){
			for (TypeRequest typeRequest : requestList) {
				page.addTargetRequest(typeRequest);
			}
		}
		
	}
	
	/**
	 * 解析数据
	 * @param page
	 * @param request
	 * @param template
	 */
	private void parseData(Page page,TypeRequest request, Template template) {
		List<TemplateRegionRule> regionRules = template.getRegionRules();
		if(CollectionUtils.isEmpty(regionRules)){
			return;
		}
		
		List<CrawlData> regionDataList = DataProcessor.applyList(regionRules, new Function<TemplateRegionRule, CrawlData>() {
			public CrawlData apply(TemplateRegionRule regionRule) {
				if(isAvalibleToParse(request,regionRule)){
					RegionData regionData = parseData(page,request,regionRule);
					CrawlData crawlData = new CrawlData(request.getTaskId(), regionData);
					crawlData.setTemplateId(template.getId());
					crawlData.setPageUrl(request.getUrl());
					
					return crawlData;
				}
				
				return null;
			};
		});
		
		page.putField(KEY_RESULT, regionDataList);
	}

	/**
	 * 
	 * @param regionRule
	 * @return
	 */
	protected abstract boolean isAvalibleToParse(TypeRequest request,TemplateRegionRule regionRule);

	protected RegionData parseData(Page page, TypeRequest request, TemplateRegionRule regionRule) {
		List<TemplateFiledRule> fieldRules = regionRule.getFieldRules();
		if(CollectionUtils.isEmpty(fieldRules)){
			return null;
		}
		
		String regionName = regionRule.getName();
		Selectable context = SelectorExpressionResolver.getPageRegionContext(page, request, regionRule.getSelectExpression());
		
		Map<String, Object> parsedFieldValues = parseFieldRules(page,context,request,fieldRules);
		
		return new RegionData(regionName, parsedFieldValues);
	}

	private Map<String, Object> parseFieldRules(Page page, Selectable context, TypeRequest request,
			List<TemplateFiledRule> fieldRules) {
		int i = 0;
        HashedMap resultMap = new HashedMap();
        List<Selectable> nodes = getNodes(context);

        for (Selectable node : nodes) {
            HashedMap childMap = new HashedMap();
            for (TemplateFiledRule fieldParseRule : fieldRules) {
                Object datas = childMap.get(fieldParseRule.getFieldName());
                if(datas == null) {
                    datas = SelectorExpressionResolver.resolve(request, node, fieldParseRule.getRule());
                } else {
                    List tmp = new ArrayList<>();
                    tmp.add(datas);
                    tmp.add(SelectorExpressionResolver.resolve(request, node, fieldParseRule.getRule()));
                    datas = tmp;
                }
                childMap.put(fieldParseRule.getFieldName(), datas);
            }
            
            resultMap.put(String.valueOf(i++), childMap);
        }
        
        return resultMap.size() > 1 ?  resultMap : (Map<String, Object>) resultMap.get("0");
	}
	
	private List<Selectable> getNodes(Selectable context) {
        List<Selectable> nodes = new LinkedList<>();

        if (context instanceof Json) {
            nodes.add(context);
        } else nodes.addAll(context.nodes());
        return nodes;
    }


	private Template getTemplate(TypeRequest request) {
		return request.getTemplateData();
	}
	
	@Override
	public int getDeepth(TypeRequest request) {
		return request.getDepth() == null ? 1 : request.getDepth();
	}

}
