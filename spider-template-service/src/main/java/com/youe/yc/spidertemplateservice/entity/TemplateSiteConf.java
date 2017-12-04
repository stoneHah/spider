package com.youe.yc.spidertemplateservice.entity;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * <p>
 * 
 * </p>
 *
 * @author qun.zheng
 * @since 2017-11-29
 */
public class TemplateSiteConf {

	/**
	 * 爬取深度
	 */
	private Integer depth;

	@Field("captcha_js_file_name")
	private String captchaJsFileName;
	@Field("captcha_judge_expression")
	private String captchaJudgeExpression;
	private String charset;
	@Field("cycle_retry_times")
	private Integer cycleRetryTimes;
	@Field("default_cookies")
	private String defaultCookies;
	private String domain;
	private String headers;
	@Field("login_js_file_name")
	private String loginJsFileName;
	@Field("login_judge_expression")
	private String loginJudgeExpression;
	@Field("retry_times")
	private Integer retryTimes;
	@Field("sleep_time")
	private Integer sleepTime;
	@Field("time_out")
	private Integer timeOut;
	
	@Field("user_agent")
	private String userAgent;

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public String getCaptchaJsFileName() {
		return captchaJsFileName;
	}

	public void setCaptchaJsFileName(String captchaJsFileName) {
		this.captchaJsFileName = captchaJsFileName;
	}

	public String getCaptchaJudgeExpression() {
		return captchaJudgeExpression;
	}

	public void setCaptchaJudgeExpression(String captchaJudgeExpression) {
		this.captchaJudgeExpression = captchaJudgeExpression;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public Integer getCycleRetryTimes() {
		return cycleRetryTimes;
	}

	public void setCycleRetryTimes(Integer cycleRetryTimes) {
		this.cycleRetryTimes = cycleRetryTimes;
	}

	public String getDefaultCookies() {
		return defaultCookies;
	}

	public void setDefaultCookies(String defaultCookies) {
		this.defaultCookies = defaultCookies;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getHeaders() {
		return headers;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}

	public String getLoginJsFileName() {
		return loginJsFileName;
	}

	public void setLoginJsFileName(String loginJsFileName) {
		this.loginJsFileName = loginJsFileName;
	}

	public String getLoginJudgeExpression() {
		return loginJudgeExpression;
	}

	public void setLoginJudgeExpression(String loginJudgeExpression) {
		this.loginJudgeExpression = loginJudgeExpression;
	}

	public Integer getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(Integer retryTimes) {
		this.retryTimes = retryTimes;
	}

	public Integer getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(Integer sleepTime) {
		this.sleepTime = sleepTime;
	}

	public Integer getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Integer timeOut) {
		this.timeOut = timeOut;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	@Override
	public String toString() {
		return "TemplateSiteConf{" +  ", captchaJsFileName="
				+ captchaJsFileName + ", captchaJudgeExpression=" + captchaJudgeExpression + ", charset=" + charset
				+ ", cycleRetryTimes=" + cycleRetryTimes + ", defaultCookies=" + defaultCookies + ", domain=" + domain
				+ ", headers=" + headers + ", loginJsFileName=" + loginJsFileName + ", loginJudgeExpression="
				+ loginJudgeExpression + ", retryTimes=" + retryTimes + ", sleepTime=" + sleepTime + ", timeOut="
				+ timeOut + "}";
	}
}
