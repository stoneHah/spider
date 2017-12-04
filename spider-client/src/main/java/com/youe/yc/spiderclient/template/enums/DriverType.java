package com.youe.yc.spiderclient.template.enums;

/**
 * 爬虫驱动类型
 */
public enum DriverType {
    HttpClient(0),WebDriver(1);

    private int code;

    DriverType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
