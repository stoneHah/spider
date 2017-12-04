package com.youe.yc.spiderclient.template.entity;

public class RequestUrl {

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";

    private String url;
    private String method;
    private String paramJson;

    public RequestUrl() {
    }

    public RequestUrl(String url) {
        this.url = url;
        this.method = METHOD_GET;
    }

    public RequestUrl(String url, String method) {
        this.url = url;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParamJson() {
        return paramJson;
    }

    public void setParamJson(String paramJson) {
        this.paramJson = paramJson;
    }
}
