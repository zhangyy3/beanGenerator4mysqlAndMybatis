package com.zhangyy3.orm.generator.bean;


import static com.zhangyy3.orm.generator.util.StrUtil.LB;

/**
 * desc:
 * author: zhangyangyang
 * date: 2015/11/13 11:30
 */
public class SqlMapBean {
    private static final String IBATIS_HEADER = "<!DOCTYPE mapper\n" +
            "        PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\n" +
            "        \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n";
    private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
    private String nameSpace;
    private ResultMap resultMap;
    private SelectSql sql;
    private String insertSQL;
    private String updateSQL;

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public ResultMap getResultMap() {
        return resultMap;
    }

    public void setResultMap(ResultMap resultMap) {
        this.resultMap = resultMap;
    }

    public SelectSql getSql() {
        return sql;
    }

    public void setSql(SelectSql sql) {
        this.sql = sql;
    }

    public void setInsertSQL(String insertSQL) {
        this.insertSQL = insertSQL;
    }

    public void setUpdateSQL(String updateSQL) {
        this.updateSQL = updateSQL;
    }

    public String genSqlMapStr() {
        StringBuilder sb = new StringBuilder();
        sb.append(XML_HEADER);
        sb.append(IBATIS_HEADER);
        sb.append("<mapper namespace=\""+nameSpace+"\">");
        sb.append(LB);
        sb.append(LB);

        if (null != resultMap) {
            sb.append(resultMap.genResultMap());
        }
        sb.append(LB);
        if (null != sql) {
            sb.append(sql.genSelectSql());
        }
        sb.append(LB);

        sb.append(LB);
        if (null != insertSQL) {
            sb.append(insertSQL);
        }
        sb.append(LB);

        sb.append(LB);
        if (null != updateSQL) {
            sb.append(updateSQL);
        }
        sb.append(LB);

        sb.append("</mapper>" + LB );

        return sb.toString();
    }
}
