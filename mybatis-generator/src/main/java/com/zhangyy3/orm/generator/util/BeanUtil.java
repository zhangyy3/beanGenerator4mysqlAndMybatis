package com.zhangyy3.orm.generator.util;

import com.zhangyy3.orm.generator.BeanModel;
import com.zhangyy3.orm.generator.bean.BeanPropBean;
import com.zhangyy3.orm.generator.bean.ColumnBean;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.zhangyy3.orm.generator.util.StrUtil.LB;
import static com.zhangyy3.orm.generator.util.StrUtil.getBlank;


/**
 * desc:
 * author: zhangyangyang
 * date: 2015/11/12 16:42
 */
public class BeanUtil {
    private static final String SEM = ";";
    //Double quotes
    private static final String DQ = "\"";
    private static DatabaseMetaData databaseMetaData;
    private static Set<String> imports;

    /**
     * 获取bean模型
     *
     * @return
     * @throws Exception
     */
    public static BeanModel genBeanModel() throws Exception {

        BeanPropBean beanPropBean = PropUtil.getBeanPropBean();
        BeanModel beanModel = new BeanModel();
        beanModel.setClassName(beanPropBean.getFileName());
        beanModel.setContent(getContent());
        beanModel.setPackageName(beanPropBean.getPackageName());
        beanModel.setTableName(PropUtil.getDbPropBean().getTableName());
        Set<ColumnBean> columnBeans = genColumnBeanSet(DbUtil.getResultSetMetaData());
        beanModel.setColumnBeans(columnBeans);
        beanModel.setImports(imports);
        return beanModel;
    }

    private static Set<ColumnBean> genColumnBeanSet(ResultSetMetaData resultSetMetaData)
            throws Exception {
        int columnCount = resultSetMetaData.getColumnCount();
        Set<ColumnBean> columnBeanSet = new HashSet<>();
        ColumnBean bean;
        String dbName;
        String beanName;
        String columnType;
        String classType;
        String[] content;
        String methodName;
        String constName;
        String simpleClassName;
        String[] temp;

        imports = new HashSet<>();

        for (int i = 1; i <= columnCount; i++) {
            bean = new ColumnBean();
            dbName = resultSetMetaData.getColumnName(i).toLowerCase();
            beanName = genBeanName(dbName);
            methodName = genMethodName(beanName);
            classType = resultSetMetaData.getColumnClassName(i);
            temp = classType.split("\\.");
            simpleClassName = temp[temp.length - 1];
            columnType = resultSetMetaData.getColumnTypeName(i);
            content = genColumnContent(resultSetMetaData, i);
            constName = ("S_" + dbName).toUpperCase();
            bean.setDbName(dbName);
            bean.setBeanName(beanName);
            bean.setMethodName(methodName);
            bean.setColumnType(columnType);
            bean.setClassType(simpleClassName);
            bean.setContent(content);
            bean.setConstName(constName);
            columnBeanSet.add(bean);
            imports.add(classType);
        }
        return columnBeanSet;
    }

    private static String genMethodName(String beanName) {
        return Character.toUpperCase(beanName.charAt(0)) + beanName.substring(1, beanName.length());
    }

    private static String[] genColumnContent(ResultSetMetaData resultSetMetaData, int i)
            throws Exception {
        List<String> content = new ArrayList<>();
        content.add("/**" + LB);
        if (resultSetMetaData.isAutoIncrement(i)) {
            content.add("* auto_increment: " + true + LB);
        }
        content.add("* dataType: " + resultSetMetaData.getColumnTypeName(i) + LB);
        content.add("* size: " + resultSetMetaData.getColumnDisplaySize(i) + LB);
        content.add("* nullable: " + (resultSetMetaData
                .isNullable(i) == ResultSetMetaData.columnNullable) + LB);
        if (databaseMetaData == null) {
            databaseMetaData = DbUtil.getDatabaseMetaData();
        }

        ResultSet resultSet = databaseMetaData
                .getColumns("", "", PropUtil.getDbPropBean().getTableName(),
                        resultSetMetaData.getColumnName(i));
        resultSet.next();
        content.add("* desc: " + resultSet.getString("Remarks") + LB);
        content.add("*/" + LB);
        return content.toArray(new String[0]);
    }

    private static String genBeanName(String dbName) {
        String[] temp = dbName.split("_");
        StringBuilder sb = new StringBuilder(temp[0]);
        if (temp.length == 1)
            return sb.toString();
        for (int i = 1; i < temp.length; i++) {
            if (StrUtil.isNotBlank(temp[i])) {
                String name = Character.toUpperCase(temp[i].charAt(0)) + temp[i]
                        .substring(1, temp[i].length());
                sb.append(name);
            }
        }
        return sb.toString();
    }

    public static String getContent() {
        String now = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());

        String content = "" +
                "/**\n" +
                "* this class is created by KbBeanTool \n" +
                "* date: " + now + "\n" +
                "* author: " + System.getProperty("user.name") + "\n" +
                "* java.version: " + System.getProperty("java.version") + "\n" +
                "*/\n";
        return content;
    }

    public static String createBean() throws Exception {
        StringBuilder sb = new StringBuilder();
        final String psfStr = "public static final String ";
        final String prv = "private ";
        BeanModel beanModel = genBeanModel();
        int blank = 0;
        sb.append(LB);
        sb.append("package "  + beanModel.getPackageName() + SEM);
        sb.append(LB);
        sb.append(LB);
        //增加import信息
        for (String im : beanModel.getImports()) {
            sb.append("import " + im + SEM + LB);
        }
        sb.append(LB);

        //添加class的注释信息
        sb.append(beanModel.getContent());
        sb.append(getBlank(blank) + "public class " + beanModel.getClassName() + " {" + LB);
        sb.append(LB);
        blank += 4;
        //Table Name
        sb.append(getBlank(blank) + "private static final String T_NAME = " + DQ + beanModel
                .getTableName() + DQ + SEM);
        sb.append(LB);
        sb.append(LB);
        //各个字段的常量
        for (ColumnBean bean : beanModel.getColumnBeans()) {
            sb.append(getBlank(blank) + psfStr + bean.getConstName() + " = " + DQ + bean
                    .getDbName() + DQ + SEM + LB);
        }
        sb.append(LB);
        sb.append(LB);

        //各个属性
        for (ColumnBean bean : beanModel.getColumnBeans()) {
            String[] contents = bean.getContent();
            for (String content : contents) {
                sb.append(getBlank(blank) + content);
            }
            sb.append(getBlank(blank) + prv + bean.getClassType() + getBlank(1) + bean
                    .getBeanName() + SEM + LB);
            sb.append(LB);
        }
        sb.append(LB);
        sb.append(LB);
        //get set
        for (ColumnBean bean : beanModel.getColumnBeans()) {
            //get
            sb.append(genGetMethod(blank, bean.getClassType(), bean.getMethodName(),
                    bean.getBeanName()));
            sb.append(LB);
            //set
            sb.append(genSetMethod(blank, bean.getClassType(), bean.getMethodName(),
                    bean.getBeanName()));
            sb.append(LB);
        }
        sb.append(LB);
        sb.append("}");
        return sb.toString();
    }

    private static String genGetMethod(int i, String classType, String methodName,
            String beanName) {
        StringBuilder sb = new StringBuilder();
        sb.append(getBlank(i) + "public " + classType + " get" + methodName + "() {" + LB);
        sb.append(getBlank(i + 4) + "return " + beanName + SEM + LB);
        sb.append(getBlank(i) + "}" + LB);
        return sb.toString();
    }

    private static String genSetMethod(int i, String classType, String methodName,
            String beanName) {
        StringBuilder sb = new StringBuilder();
        sb.append(getBlank(
                i) + "public void " + "set" + methodName + "(" + classType + " " + beanName + ")" + " {" + LB);
        sb.append(getBlank(i + 4) + "this." + beanName + " = " + beanName + SEM + LB);
        sb.append(getBlank(i) + "}" + LB);
        return sb.toString();
    }
}
