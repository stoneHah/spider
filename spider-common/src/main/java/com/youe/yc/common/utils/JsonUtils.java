package com.youe.yc.common.utils;

import com.alibaba.fastjson.JSON;

public class JsonUtils {
    public static String toJsonString(Object obj) {
        return JSON.toJSONString(obj);
    }

    public static <T> T parseObject(String jsonStr,Class<T> clazz) {
        return JSON.parseObject(jsonStr, clazz);
    }
}
