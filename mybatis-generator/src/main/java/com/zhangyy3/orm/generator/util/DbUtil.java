package com.zhangyy3.orm.generator.util;


import com.zhangyy3.orm.generator.bean.DBPropBean;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * desc:
 * author: zhangyangyang
 * date: 2015/11/12 16:00
 */
public class DbUtil {

    public static ResultSetMetaData getResultSetMetaData()
            throws SQLException, ClassNotFoundException {
        return getResultSet().getMetaData();
    }


    public static DatabaseMetaData getDatabaseMetaData() throws Exception{
        return getConnection().getMetaData();
    }

    private static ResultSet getResultSet() throws SQLException, ClassNotFoundException {
        DBPropBean dbPropBean = PropUtil.getDbPropBean();
        String sql = "select * from " + dbPropBean.getTableName() + " limit 1";
        Statement statement = getConnection().createStatement();
        return statement.executeQuery(sql);
    }

    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        DBPropBean dbPropBean = PropUtil.getDbPropBean();
        Class.forName(dbPropBean.getDriverClass());
        Connection connection = DriverManager.getConnection(dbPropBean.getUrl(), dbPropBean
                        .getName(), dbPropBean.getPwd());
        return connection ;
    }

}
