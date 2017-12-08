package com.youe.yc.spiderservice.util;

import us.codecraft.webmagic.utils.UrlUtils;

public class DBUtils {

    /**
     * 获取 url对应的的mongodb 集合名
     * @param url
     * @return
     */
    public static String determineMongoCollection(String url) {
        return UrlUtils.getDomain(url).replace(".", "_");
    }
}
