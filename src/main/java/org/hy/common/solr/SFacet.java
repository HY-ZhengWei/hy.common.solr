package org.hy.common.solr;

import java.util.ArrayList;
import java.util.List;

import org.hy.common.Help;
import org.hy.common.solr.field.SFieldFacetPrefix;
import org.hy.common.xml.SerializableDef;





/**
 * Solr查询'平面'的统计 
 *
 * @author      ZhengWei(HY)
 * @createDate  2014-12-10
 * @version     v1.0
 */
public class SFacet extends SerializableDef
{
    private static final long serialVersionUID = -1520373442433333172L;
    

    /** 查询条件 */
    private String                     query;
    
    /** 分类统计的字段 */
    private List<String>               fields;
    
    /** 前缀匹配 */
    private SFieldFacetPrefix          prefix;
    
    /** 限制 Facet 字段返回的结果条数 */
    private Integer                    limit;

    
    
    public SFacet()
    {
        this(null);
    }
    
    
    
    public SFacet(String i_FacetField)
    {
        this.setField(i_FacetField);
    }
    
    
    
    public SFacet(String i_FacetField ,SFieldFacetPrefix i_Prefix)
    {
        this.setField(     i_FacetField);
        this.setPrefixInfo(i_Prefix);
    }
    
    
    
    /**
     * 是否启用统计功能
     * 
     * @return
     */
    public boolean isAllowFacet()
    {
        return !Help.isNull(this.fields);
    }
    
    
    
    /**
     * 获取：查询条件
     */
    public String getQuery()
    {
        return query;
    }

    
    /**
     * 设置：查询条件
     * 
     * @param queryWord 
     */
    public void setQuery(String i_QueryWord)
    {
        if ( i_QueryWord == null )
        {
            this.query = null;
        }
        else
        {
            this.query = i_QueryWord.trim();
        }
    }

    
    /**
     * 获取：分类统计的字段
     */
    public List<String> getFields()
    {
        return fields;
    }

    
    /**
     * 设置：分类统计的字段
     * 
     * @param fields 
     */
    public void setFields(List<String> fields)
    {
        this.fields = fields;
    }
    
    
    
    /**
     * 别名：可与Solr的命名看齐
     *
     * @param i_FacetField
     */
    public synchronized void setField(String i_FacetField)
    {
        this.addField(i_FacetField);
    }
    
    
    
    /**
     * 添加统计字段
     * 
     * @param i_FacetField
     */
    public synchronized void setAddField(String i_FacetField)
    {
        this.addField(i_FacetField);
    }
    
    
    
    /**
     * 添加统计字段
     * 
     * @param i_FacetField
     */
    public synchronized void addField(String i_FacetField)
    {
        if ( !Help.isNull(i_FacetField) )
        {
            if ( this.fields == null )
            {
                this.fields = new ArrayList<String>();
            }
            
            this.fields.add(i_FacetField.trim());
        }
    }
    

    
    /**
     * 获取：前缀匹配
     */
    public SFieldFacetPrefix getPrefixInfo()
    {
        return prefix;
    }
    
    
    
    /**
     * 设置：前缀匹配
     * 
     * @param prefix 
     */
    public void setPrefix(String i_Prefix)
    {
        this.prefix = new SFieldFacetPrefix(null ,i_Prefix);
    }

    
    
    /**
     * 设置：前缀匹配
     * 
     * @param prefix 
     */
    public void setPrefixInfo(SFieldFacetPrefix prefix)
    {
        this.prefix = prefix;
    }


    
    /**
     * 获取：限制 Facet 字段返回的结果条数
     */
    public Integer getLimit()
    {
        return limit;
    }


    
    /**
     * 设置：限制 Facet 字段返回的结果条数
     * 
     * @param limit 
     */
    public void setLimit(Integer limit)
    {
        this.limit = limit;
    }
    
}
