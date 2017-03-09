package com.zhangyy3.orm.generator.bean;

/**
 * desc:
 * author: zhangyangyang
 * date: 2015/11/12 16:03
 */
public class DBPropBean {
    private String name;
    private String pwd;
    private String url;
    private String driverClass;


    private String tableName;

    public DBPropBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
