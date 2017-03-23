package org.hy.common.solr;

import org.hy.common.Help;
import org.hy.common.xml.SerializableDef;





/**
 * Solr的查询条件字段信息
 * 1. Solr查询中的过滤条件           -- 对应命令为 fq
 * 2. Solr查询中对结果集排序的查询条件 -- 对应命令为 sort
 * 
 * @author      ZhengWei(HY)
 * @createDate  2014-12-05
 * @version     v1.0
 */
public abstract class SField<V> extends SerializableDef
{
    
    private static final long serialVersionUID = -7223081566049139383L;
    

    /** 默认分割符 */
    public final static String $Default_Split    = ":";
    
    /** 默认查询所有的字符 */
    public final static String $Default_QueryAll = "*";
    
    
    
    /** 字段名称 */
    protected String   fieldName;
    
    /** 字段值 */
    protected V        fieldValue;
    

    
    /**
     * 设置：字段值
     * 
     * @param i_FieldValue 
     */
    public abstract void setFieldValue(V i_FieldValue);
    
    
    
    /**
     * 用于返回Solr命令字符串
     *
     * @author      ZhengWei(HY)
     * @createDate  2014-12-06
     * @version     v1.0
     *
     * @return
     *
     * @see java.lang.Object#toString()
     */
    public abstract String toString();
    
    
    
    public SField()
    {
        this(null ,null);
    }
    
    
    
    public SField(String i_FieldName)
    {
        this(i_FieldName ,null);
    }
    
    
    
    public SField(String i_FieldName ,V i_FiledValue)
    {
        this.setFieldName( i_FieldName);
        this.setFieldValue(i_FiledValue);
    }
    
    
    
    /**
     * 获取：字段名称
     */
    public String getFieldName()
    {
        return fieldName;
    }

    
    
    /**
     * 设置：字段名称
     * 
     * @param fieldName 
     */
    public void setFieldName(String i_FieldName)
    {
        if ( Help.isNull(i_FieldName) )
        {
            this.fieldName = null;
        }
        else
        {
            this.fieldName = i_FieldName.trim();
        }
    }
    
    
    
    /**
     * 获取：字段值
     */
    public V getFieldValue()
    {
        return fieldValue;
    }
    
    
    
    /**
     * 别名：可与Solr的命名看齐
     * 
     * @param i_FieldName
     */
    public void setField(String i_FieldName)
    {
        this.setFieldName(i_FieldName);
    }
    
    
    
    /**
     * 别名：可与Solr的命名看齐
     * 
     * @param i_FieldValue
     */
    public void setValue(V i_FieldValue)
    {
        this.setFieldValue(i_FieldValue);
    }
    

}
