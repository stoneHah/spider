package com.youe.yc.spiderservice.core.persistent;


import com.youe.yc.spiderservice.core.entity.CrawlData;

public interface IResultPersistentService {

    /**
     * 保存爬取出来的数据
     * @param crawlData
     * @return
     */
    boolean saveCrawlerResult(CrawlData crawlData);

    /**
     * 获取支持的持久化数据类型（普通数据，图片，文档等）
     * @return
     */
    boolean isSupportedByDataType(String dataType);

    /**
     * 
     * @return
     */
	String getDesc();

}
