package com.yangkaile.admin.common.mybatis;

import java.util.Map;

/**
 * MyBaseEntity对象，用于MyBaseMapper的增删改查操作
 * @author yangkaile
 * @date 2018-11-29 14:10:24
 */
public class MyBaseEntity {

    /**
     * id
     */
    private int id;
    /**
     * 分页查询时设置的页面大小
     */
    private int pageSize ;
    /**
     * 分页查询时设置的起始行
     */
    private int startRows ;
    /**
     * 默认查询字段
     */
    private String defaultFields;
    /**
     * 表名
     */
    private String tableName;

    /**
     * 自定义字段
     */
    private String customizedFields;


//    private Map<String,String> fieldMap;
//
//    public MyBaseEntity(String tableName,String defaultFields,Map<String,String> fieldMap){
//        this.tableName = tableName;
//        this.defaultFields = defaultFields;
//       this.fieldMap = fieldMap;
//    }

    public MyBaseEntity(String tableName,String defaultFields){
        this.tableName = tableName;
        this.defaultFields = defaultFields;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartRows() {
        return startRows;
    }

    public void setStartRows(int startRows) {
        this.startRows = startRows;
    }

    public String getDefaultFields() {
        return defaultFields;
    }

    public String getTableName() {
        return tableName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomizedFields() {
        return customizedFields;
    }

    public void setCustomizedFields(String ... field) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0 ; i < field.length ; i ++){
            if(i >= field.length -1){
                builder.append(field[i]);

            }else {
                builder.append(field[i]).append(",");
            }
        }
        this.customizedFields = builder.toString();
    }

    //    public Map<String, String> getFieldMap() {
//        return fieldMap;
//    }

    @Override
    public String toString() {
        return "MyBaseEntity{" +
                "id=" + id +
                ", pageSize=" + pageSize +
                ", startRows=" + startRows +
                ", defaultFields='" + defaultFields + '\'' +
                ", tableName='" + tableName + '\'' +
                ", customizedFields=" + customizedFields +
                '}';
    }
}
