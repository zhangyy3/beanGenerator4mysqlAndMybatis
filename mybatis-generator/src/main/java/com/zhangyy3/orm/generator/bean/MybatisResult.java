package com.zhangyy3.orm.generator.bean;

/**
 * desc:
 * author: zhangyangyang
 * date: 2015/11/13 11:35
 */
public class MybatisResult {
    private String property;
    private String column;

    public MybatisResult() {

    }

    public MybatisResult(String property, String column) {
        this.property = property;
        this.column = column;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }
}
