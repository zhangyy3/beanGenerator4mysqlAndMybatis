package com.zhangyy3.orm.generator;

import com.zhangyy3.orm.generator.bean.BeanPropBean;
import com.zhangyy3.orm.generator.bean.DBPropBean;
import com.zhangyy3.orm.generator.bean.MybatisPropBean;
import com.zhangyy3.orm.generator.bean.SqlMapBean;
import com.zhangyy3.orm.generator.util.BeanUtil;
import com.zhangyy3.orm.generator.util.FileUtil;
import com.zhangyy3.orm.generator.util.MybatisUtil;
import com.zhangyy3.orm.generator.util.PropUtil;

/**
 * start
 */
public class App {
    public static void main(String[] args) throws Exception {
        genBeanFile();
        genMybatisFile();
    }

    private static void genBeanFile() throws Exception {
        System.out.println("开始生成javabean");
        String ret = BeanUtil.createBean();
        BeanPropBean beanPropBean = PropUtil.getBeanPropBean();
        String path = beanPropBean.getFilePath();
        String fileName = beanPropBean.getFileName() + ".java";
        FileUtil.saveFile(path, fileName, ret);
        System.out.println("生成javabean文件成功");
        System.out.println("生成的文件路径: " + path);
        System.out.println("生成的文件: " + fileName);
    }


    private static void genMybatisFile() throws Exception {
        MybatisPropBean mybatisPropBean = PropUtil.getMybatisPropBean();
        if (mybatisPropBean.isOpen()) {
            SqlMapBean mapBean = MybatisUtil.genSqlMapBean();
            System.out.println("开始生成Mybatis文件");
            DBPropBean dbPropBean = PropUtil.getDbPropBean();
            String ret = mapBean.genSqlMapStr();
            String fileName = dbPropBean.getTableName() + ".sql.xml";
            String filePath = PropUtil.getBeanPropBean().getFilePath();
            FileUtil.saveFile(filePath, fileName, ret);
            System.out.println("生成Mybatis文件:" + fileName);
            System.out.println("生成的文件路径: " + filePath);

        }
    }

}
