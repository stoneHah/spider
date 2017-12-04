package com.youe.yc.spiderservice.core.pageprocessor;


import com.youe.yc.spiderservice.core.request.RequestType;
import com.youe.yc.spiderservice.core.request.TypeRequest;

/**
 * 请求处理工厂
 * @author ibm
 *
 */
public class RequestProcessorFactory {
	
	/**
	 * 获取请求处理器
	 * @param request
	 * @return
	 */
	public static RequestProcessor getRequestProcessor(TypeRequest request){
		RequestProcessor result = null;
		
		RequestType type = request.getType();
		
		switch (type) {
			case Seed:
				result = new SeedRequestProcessor();
				break;
			case Target:
				result = new TargetRequestProcessor();
				break;
			default:
				break;
		}
		
		return result;
	}
}
