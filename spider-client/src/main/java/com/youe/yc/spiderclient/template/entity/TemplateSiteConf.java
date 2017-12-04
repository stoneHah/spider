package com.youe.yc.spiderclient.template.entity;

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

	private String captchaJsFileName;
	private String captchaJudgeExpression;
	private String charset;
	private Integer cycleRetryTimes;
	private String defaultCookies;
	private String domain;
	private String headers;
	private String loginJsFileName;
	private String loginJudgeExpression;
	private Integer retryTimes;
	private Integer sleepTime;
	private Integer timeOut;
	
	private String userAgent;

	public Integer getDepth() {
		return depth == null ? 2 : depth;
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
