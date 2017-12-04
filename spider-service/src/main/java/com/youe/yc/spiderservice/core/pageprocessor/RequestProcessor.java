package com.youe.yc.spiderservice.core.pageprocessor;


import com.youe.yc.spiderservice.core.request.TypeRequest;
import us.codecraft.webmagic.Page;

/**
 * 请求处理器
 * @author ibm
 *
 */
public interface RequestProcessor {
	public static final String KEY_RESULT = "com.youedata.yc.spider.service.pageprocessor.RequestProcessor.result";
	
	void process(Page page, TypeRequest request);
	
	/**
	 * 获取当前请求的深度
	 * @param request
	 * @return
	 */
	int getDeepth(TypeRequest request);
}
