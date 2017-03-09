package com.zhangyy3.orm.generator.bean;

import java.util.Set;

import static com.zhangyy3.orm.generator.util.StrUtil.LB;
import static com.zhangyy3.orm.generator.util.StrUtil.getBlank;


/**
 * desc:
 * author: zhangyangyang
 * date: 2015/11/13 11:33
 */
public class SelectSql {
    private Set<ColumnBean> columnBeanSet;
    private String tableName;
    private String clazzName;

    public SelectSql(Set<ColumnBean> columnBeanSet, String tableName, String fullClazzName) {
        this.columnBeanSet = columnBeanSet;
        this.tableName = tableName;
        this.clazzName = fullClazzName;
    }

    public SelectSql() {

    }

    public Set<ColumnBean> getColumnBeanSet() {
        return columnBeanSet;
    }

    public void setColumnBeanSet(Set<ColumnBean> columnBeanSet) {
        this.columnBeanSet = columnBeanSet;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String genSelectSql() {
        StringBuilder sb = new StringBuilder();
        sb.append(getBlank(4) + "<sql id=\"selectAll\">" + LB);
        sb.append(getBlank(8) + "select" + LB);
        ColumnBean[] beans = columnBeanSet.toArray(new ColumnBean[0]);
        int length = beans.length;
        for (int i = 0; i < length - 1; i ++ ) {
            sb.append(getBlank(8) + beans[i].getDbName() + " AS " + beans[i].getBeanName() + LB);
        }
        sb.append(getBlank(8) + beans[length-1].getBeanName() + " AS " + beans[length-1]
                .getBeanName() + LB);
        sb.append(getBlank(8) + "FROM " + tableName + LB);
        sb.append(getBlank(4) + "</sql>" + LB);
        return sb.toString();
    }
}
