package com.zhangyy3.orm.generator.bean;

/**
 * desc:
 * author: zhangyangyang
 * date: 2015/11/12 16:04
 */
public class MybatisPropBean {
    private boolean open;
    private boolean resultMap;
    private boolean selectAll;
    private boolean insert;
    private boolean update;

    public MybatisPropBean() {
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isResultMap() {
        return resultMap;
    }

    public void setResultMap(boolean resultMap) {
        this.resultMap = resultMap;
    }

    public boolean isSelectAll() {
        return selectAll;
    }

    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
    }

    public boolean isInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}
