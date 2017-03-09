package com.zhangyy3.orm.generator.bean;

import java.util.Set;

import static com.zhangyy3.orm.generator.util.StrUtil.LB;
import static com.zhangyy3.orm.generator.util.StrUtil.getBlank;


/**
 * desc:
 * author: zhangyangyang
 * date: 2015/11/13 11:33
 */
public class ResultMap {
    private String id;
    private String type;
    private Set<MybatisResult> resultSet;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<MybatisResult> getResultSet() {
        return resultSet;
    }

    public void setResultSet(Set<MybatisResult> resultSet) {
        this.resultSet = resultSet;
    }

    public String genResultMap() {
        StringBuilder sb = new StringBuilder();
        sb.append(getBlank(4) + "<resultMap id=\"" + id + "\" type=\"" + type + "\">" + LB);
        for (MybatisResult result : resultSet) {
            sb.append(getBlank(8) + "<result property=\"" + result.getProperty() + "\" " +
                    "column=\"" + result.getColumn() + "\" />" + LB);
        }
        sb.append(getBlank(4) + "</resultMap>" + LB);
        return sb.toString();
    }
}
