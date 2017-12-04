package com.youe.yc.spiderclient.template.entity;

import java.util.Date;
import java.util.List;

public class Template {
    private String id;
    private String name;

    private RequestUrl seedUrl;

    private List<RequestUrl> startUrls;

    private String[] urlRegex;

    private int driver;

    private String isJsRendering;

    /**
     * 页面校验规则
     */
    private String pageValidationRule;

    private TemplateSiteConf siteConf = null;

    private List<TemplateRegionRule> regionRules;

    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
    private int depth;

    public int getDriver() {
        return driver;
    }

    public void setDriver(int driver) {
        this.driver = driver;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RequestUrl getSeedUrl() {
        return seedUrl;
    }

    public void setSeedUrl(RequestUrl seedUrl) {
        this.seedUrl = seedUrl;
    }

    public List<RequestUrl> getStartUrls() {
        return startUrls;
    }

    public void setStartUrls(List<RequestUrl> startUrls) {
        this.startUrls = startUrls;
    }

    public String[] getUrlRegex() {
        return urlRegex;
    }

    public void setUrlRegex(String[] urlRegex) {
        this.urlRegex = urlRegex;
    }

    public String getIsJsRendering() {
        return isJsRendering;
    }

    public void setIsJsRendering(String isJsRendering) {
        this.isJsRendering = isJsRendering;
    }

    public String getPageValidationRule() {
        return pageValidationRule;
    }

    public void setPageValidationRule(String pageValidationRule) {
        this.pageValidationRule = pageValidationRule;
    }

    public TemplateSiteConf getSiteConf() {
        return siteConf;
    }

    public void setSiteConf(TemplateSiteConf siteConf) {
        this.siteConf = siteConf;
    }

    public List<TemplateRegionRule> getRegionRules() {
        return regionRules;
    }

    public void setRegionRules(List<TemplateRegionRule> regionRules) {
        this.regionRules = regionRules;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getDepth() {
        return siteConf == null ? 2 : siteConf.getDepth();
    }
}
