package com.zhangyy3.orm.generator.util;


import com.zhangyy3.orm.generator.BeanModel;
import com.zhangyy3.orm.generator.bean.ColumnBean;
import com.zhangyy3.orm.generator.bean.MybatisPropBean;
import com.zhangyy3.orm.generator.bean.MybatisResult;
import com.zhangyy3.orm.generator.bean.ResultMap;
import com.zhangyy3.orm.generator.bean.SelectSql;
import com.zhangyy3.orm.generator.bean.SqlMapBean;

import java.util.HashSet;
import java.util.Set;

/**
 * desc:
 * author: zhangyangyang
 * date: 2015/11/13 11:25
 */
public class MybatisUtil {

    public static SqlMapBean genSqlMapBean() throws Exception {
        BeanModel model = BeanUtil.genBeanModel();
        MybatisPropBean propBean = PropUtil.getMybatisPropBean();
        SqlMapBean sqlMapBean = new SqlMapBean();
        sqlMapBean.setNameSpace(model.getTableName());
        if (propBean.isResultMap()) {
            ResultMap resultMap = genResultMap(model.getPackageName(), model.getClassName(),
                    model.getColumnBeans());
            sqlMapBean.setResultMap(resultMap);
        }

        if (propBean.isSelectAll()) {
            SelectSql selectSql = genSelectSql(model.getTableName(), model.getPackageName(),
                    model.getClassName(), model.getColumnBeans());
            sqlMapBean.setSql(selectSql);
        }

        if (propBean.isInsert()) {
            sqlMapBean.setInsertSQL(genInsertSQL(model));
        }

        if (propBean.isUpdate()) {
            sqlMapBean.setUpdateSQL(genUpdateSQL(model));
        }


        return sqlMapBean;
    }

    private static String genUpdateSQL(BeanModel model) {
        StringBuilder sb = new StringBuilder();
        // head
        sb.append(StrUtil.getBlank(4));
        sb.append("<update id=\"update_");
        sb.append(model.getTableName());
        sb.append("\">");
        sb.append(StrUtil.LB);
        // body
        sb.append(StrUtil.getBlank(8));
        sb.append("UPDATE `");
        sb.append(model.getTableName());
        sb.append("` set");
        sb.append(StrUtil.LB);
        Set<ColumnBean> columnBeenSet = model.getColumnBeans();
        for (ColumnBean bean : columnBeenSet) {
            sb.append(StrUtil.getBlank(12));
            sb.append("`");
            sb.append(bean.getDbName()).append("`=");
            sb.append("#{").append(bean.getBeanName()).append("}").append(",");
            sb.append(StrUtil.LB);
        }

        sb.append(StrUtil.LB);
        // tail
        sb.append(StrUtil.getBlank(4));
        sb.append("</update>");
        sb.append(StrUtil.LB);
        return sb.toString();
    }

    private static SelectSql genSelectSql(String tableName, String packageName, String className,
                                          Set<ColumnBean> columnBeans) {
        String fullClazzName = packageName + "." + className;
        return new SelectSql(columnBeans, tableName, fullClazzName);
    }

    private static ResultMap genResultMap(String packageName, String className,
                                          Set<ColumnBean> columnBeans) {
        ResultMap resultMap = new ResultMap();
        resultMap.setId("rM" + className);
        resultMap.setType(packageName + "." + className);
        Set<MybatisResult> resultSet = genMybatisResult(columnBeans);
        resultMap.setResultSet(resultSet);
        return resultMap;
    }

    private static Set<MybatisResult> genMybatisResult(Set<ColumnBean> columnBeans) {
        Set<MybatisResult> mybatisResults = new HashSet<>();
        for (ColumnBean bean : columnBeans) {
            mybatisResults.add(new MybatisResult(bean.getBeanName(), bean.getDbName()));
        }
        return mybatisResults;
    }

    private static String genInsertSQL(BeanModel model) {
        StringBuilder sb = new StringBuilder();
//        header
        sb.append(StrUtil.getBlank(4));
        sb.append("<insert id=\"insert_");
        sb.append(model.getTableName());
        sb.append("\"");
        sb.append(">");
        sb.append(StrUtil.LB);

        // body
        sb.append(StrUtil.getBlank(8));
        sb.append("INSERT INTO ");
        sb.append("`");
        sb.append(model.getTableName());
        sb.append("`");
        sb.append(" ( ");
        Set<ColumnBean> columnBeans = model.getColumnBeans();
        for (ColumnBean columnBean : columnBeans) {
            sb.append("`").append(columnBean.getDbName()).append("`").append(", ");
        }
        // remove last ','
        sb = new StringBuilder(sb.substring(0, sb.length() - 2));
        sb.append(")").append(StrUtil.LB);
        sb.append(StrUtil.getBlank(8));
        sb.append("VALUES").append("(");
        for (ColumnBean columnBean : columnBeans) {
            sb.append("#").append("{").append(columnBean.getBeanName()).append("}").append(", ");
        }
        sb = new StringBuilder(sb.substring(0, sb.length() - 2));
        sb.append(");").append(StrUtil.LB);

        //tail
        sb.append(StrUtil.getBlank(4));
        sb.append("</insert>");
        sb.append(StrUtil.LB);

        return sb.toString();
    }

}
