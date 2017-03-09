package com.zhangyy3.orm.generator;


import com.zhangyy3.orm.generator.bean.ColumnBean;

import java.util.Set;

/**
 * desc:
 * author: zhangyangyang
 * date: 2015/11/12 16:34
 */
public class BeanModel {
    private String className;
    private String packageName;
    private String content;
    private String tableName;
    private Set<ColumnBean> columnBeans;
    private Set<String> imports;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<ColumnBean> getColumnBeans() {
        return columnBeans;
    }

    public void setColumnBeans(Set<ColumnBean> columnBeans) {
        this.columnBeans = columnBeans;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Set<String> getImports() {
        return imports;
    }

    public void setImports(Set<String> imports) {
        this.imports = imports;
    }
}
