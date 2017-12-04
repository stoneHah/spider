package com.youe.yc.spiderservice.core.pageprocessor;

import com.youe.yc.spiderclient.template.entity.Template;
import com.youe.yc.spiderclient.template.service.TemplateService;
import com.youe.yc.spiderservice.core.request.RequestType;
import com.youe.yc.spiderservice.core.request.TypeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 
 * @author ibm
 *
 */
@Component(value = "genericPageProcessor")
public class GenericPageProcessor implements PageProcessor {
    private static Logger logger = LoggerFactory.getLogger(GenericPageProcessor.class);
    
    @Autowired
    private TemplateService templateService;

    @Override
    public void process(Page page) {
        TypeRequest request = (TypeRequest) page.getRequest();
        
        //添加额外数据
        addExtreData(request);
        
        RequestProcessor requestProcessor = RequestProcessorFactory.getRequestProcessor(request);
        
        if(requestProcessor != null){
        	requestProcessor.process(page, request);
        }else{
        	logger.warn("无法处理请求:" + request.getUrl());
        }
    }

    private void addExtreData(TypeRequest request) {
		if(RequestType.Seed == request.getType()){
			String templateId = request.getTemplateId();
			
			Template template = templateService.getTemplate(templateId);
			request.setTemplateData(template);
		}
	}

	@Override
	public Site getSite() {
		return Site.me().setRetryTimes(3).setSleepTime(1000);
	}

}
