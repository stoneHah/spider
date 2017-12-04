package com.youe.yc.spiderservice.core.spider;

import com.alibaba.fastjson.JSON;
import com.youe.yc.spiderclient.template.entity.Template;
import com.youe.yc.spiderclient.template.entity.TemplateSiteConf;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.UrlUtils;

import java.util.ArrayList;
import java.util.Map;

/**
 * 
 * @author ibm
 *
 */
public class YoueSpider extends Spider {
	
	private CompleteCallback completeCallback;

	public YoueSpider(Template template, PageProcessor pageProcessor) {
		super(pageProcessor);
		this.setSpiderListeners(new ArrayList<SpiderListener>());

		this.site = getSite(template);
	}

	private Site getSite(Template template) {
		String domain = UrlUtils.getDomain(template.getSeedUrl().getUrl());
		
		Site site = Site.me();
		site.setSleepTime(1000);
		site.setDomain(domain);
		site.addHeader("host", domain);
		site.setUserAgent(
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.111 Safari/537.36");
		site.setCharset("utf8");
		// 默认重试3次
		site.setRetryTimes(3);

		TemplateSiteConf siteConf = template.getSiteConf();
		site.setCycleRetryTimes(4);
		if (siteConf != null) {
			// site.setDomain(pageSite.getDomain());
			site.setCharset(siteConf.getCharset());
			site.setCycleRetryTimes(siteConf.getCycleRetryTimes());
			site.setRetryTimes(siteConf.getRetryTimes());
			int sleepTime = siteConf.getSleepTime();
			site.setSleepTime(sleepTime);
			site.setUserAgent(siteConf.getUserAgent());
			site.setUseGzip(true);

			if (StringUtils.hasText(siteConf.getHeaders())) {
				try {
					Map<String, String> headMap = JSON.parseObject(siteConf.getHeaders(), Map.class);
					for (Map.Entry<String, String> entry : headMap.entrySet()) {
						site.addHeader(entry.getKey(), entry.getValue());
					}
				} catch (Exception ex) {
				}
			}
			if (StringUtils.hasText(siteConf.getDefaultCookies())) {
				try {
					Map<String, String> cookiesMap = JSON.parseObject(siteConf.getDefaultCookies(), Map.class);
					for (Map.Entry<String, String> entry : cookiesMap.entrySet()) {
						site.addCookie(domain, entry.getKey(), entry.getValue());
					}
				} catch (Exception ex) {
				}
			}
		}
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
	
	public YoueSpider uuid(String uuid){
		this.uuid = uuid;
		return this;
	}
	
	public YoueSpider completeCallback(CompleteCallback completeCallback){
		this.completeCallback = completeCallback;
		
		return this;
	}
	
	@Override
	public void run() {
		super.run();
		onComplete();
	}

	private void onComplete() {
		if(completeCallback != null){
			completeCallback.apply();
		}
	}

	@Override
	protected void onError(Request request) {
		// 失败后换代理重试一次
		// Integer tryTimes = (Integer) request.getExtra("tryTimes");
		// if (tryTimes == null)
		// tryTimes = 0;
		// if (tryTimes == 0) {
		// // TODO 异常恢复
		// this.site = pageSiteService.getSite(domain, true);
		// request.putExtra("tryTimes", ++tryTimes);
		// request.getExtras().remove("cookieIds");
		// this.addRequest(request);
		// } else
		// super.onError(request);
	}
	
	public static interface CompleteCallback{
		void apply();
	}

}
