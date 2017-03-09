package com.zhangyy3.orm.generator.util;


import com.zhangyy3.orm.generator.bean.BeanPropBean;
import com.zhangyy3.orm.generator.bean.DBPropBean;
import com.zhangyy3.orm.generator.bean.MybatisPropBean;

import java.io.IOException;
import java.util.Properties;

/**
 * desc:
 * author: zhangyangyang
 * date: 2015/11/12 16:00
 */
public class PropUtil {
    private static BeanPropBean beanPropBean;
    private static MybatisPropBean mybatisPropBean;
    private static DBPropBean dbPropBean;

    public static BeanPropBean getBeanPropBean() {
        if (null != beanPropBean)
            return beanPropBean;
        beanPropBean = new BeanPropBean();
        Properties properties = load("bean.properties");
        beanPropBean.setFileName(properties.getProperty("fileName"));
        beanPropBean.setFilePath(properties.getProperty("filePath"));
        beanPropBean.setPackageName(properties.getProperty("packageName"));
        properties.clear();
        return beanPropBean;
    }

    public static MybatisPropBean getMybatisPropBean() {
        if (null != mybatisPropBean)
            return mybatisPropBean;

        mybatisPropBean = new MybatisPropBean();
        Properties properties = load("mybatis.properties");
        mybatisPropBean.setOpen(Boolean.parseBoolean(properties.getProperty("open")));
        mybatisPropBean.setResultMap(Boolean.parseBoolean(properties.getProperty("resultMap")));
        mybatisPropBean.setSelectAll(Boolean.parseBoolean(properties.getProperty("selectAll")));
        properties.clear();
        return mybatisPropBean;
    }



    public static DBPropBean getDbPropBean() {
        if (null != dbPropBean)
            return dbPropBean;
        dbPropBean = new DBPropBean();
        Properties properties = load("dbconfig.properties");
        dbPropBean.setTableName(properties.getProperty("tableName"));
        dbPropBean.setDriverClass(properties.getProperty("driverClass"));
        dbPropBean.setName(properties.getProperty("name"));
        dbPropBean.setPwd(properties.getProperty("pwd"));
        dbPropBean.setUrl(properties.getProperty("url"));
        properties.clear();
        return dbPropBean;
    }

    private static Properties load(String fileName) {
        Properties properties = new Properties();
        try {

            properties.load(PropUtil.class.getClassLoader().getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
}
