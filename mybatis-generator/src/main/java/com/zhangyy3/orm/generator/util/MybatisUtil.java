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

        return sqlMapBean;
    }

    private static SelectSql genSelectSql(String tableName, String packageName, String className,
            Set<ColumnBean> columnBeans) {
        String fullClazzName = packageName + "." + className;
        SelectSql selectSql = new SelectSql(columnBeans, tableName, fullClazzName);
        return selectSql;
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

}
