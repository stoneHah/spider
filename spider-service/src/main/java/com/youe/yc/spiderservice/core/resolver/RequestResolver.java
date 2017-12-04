package com.youe.yc.spiderservice.core.resolver;

import com.youe.yc.spiderservice.core.request.RequestType;
import com.youe.yc.spiderservice.core.request.TypeRequest;
import com.youe.yc.spiderservice.util.DataProcessor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * 请求解析器
 * @author ibm
 *
 */
public class RequestResolver {
	
	/**
	 * 解析Request
	 * @param context
	 * @param commaDelimitedUrlRegex
	 * @return
	 */
	public static List<TypeRequest> parseRequest(Selectable context,String commaDelimitedUrlRegex,Function<TypeRequest.TypeRequestBuilder, Void> requestCallback){
		if(!StringUtils.hasText(commaDelimitedUrlRegex)){
			return null;
		}
		
		return parseRequest(context, StringUtils.commaDelimitedListToStringArray(commaDelimitedUrlRegex),requestCallback);
	}

	public static List<TypeRequest> parseRequest(Selectable context, String[] urlRegexAry) {
		return parseRequest(context, urlRegexAry, null);
	}
	
	public static List<TypeRequest> parseRequest(Selectable context, String[] urlRegexAry,Function<TypeRequest.TypeRequestBuilder, Void> requestCallback) {
		if(ArrayUtils.isEmpty(urlRegexAry)){
			return null;
		}
		
		List<String> allLinks = context.links().all();
		if(CollectionUtils.isEmpty(allLinks)){
			return null;
		}
		
		return DataProcessor.applyList(allLinks, new Function<String, TypeRequest>() {
			@Override
			public TypeRequest apply(String link) {
				for (String urlRegex : urlRegexAry) {
					if(Pattern.matches(urlRegex, link)){
						TypeRequest.TypeRequestBuilder typeRequestBuilder = new TypeRequest.TypeRequestBuilder(link, RequestType.Target);
						if(requestCallback != null){
							requestCallback.apply(typeRequestBuilder);
						}
						return typeRequestBuilder.build();
					}
				}
				return null;
			}
		});
	} 
}
