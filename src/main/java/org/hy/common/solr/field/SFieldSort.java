package org.hy.common.solr.field;

import org.apache.solr.client.solrj.SolrQuery.ORDER;

import org.hy.common.Help;
import org.hy.common.solr.SField;
import org.hy.common.solr.enums.ESortType;





/**
 * Solr的查询条件字段信息 -- 对应命令：sort
 * 
 * Solr默认为：降序
 * 
 * @author      ZhengWei(HY)
 * @createDate  2014-12-06
 * @version     v1.0
 */
public class SFieldSort extends SField<ORDER>
{
    
    private static final long serialVersionUID = -5946170028014593623L;



    public SFieldSort()
    {
        this(null ,(ORDER)null);
    }
    
    
    
    public SFieldSort(String i_FieldName)
    {
        this(i_FieldName ,(ORDER)null);
    }
    
    
    
    public SFieldSort(String i_FieldName ,String i_FiledValue)
    {
        super(i_FieldName ,null);
        
        if ( Help.isNull(i_FiledValue) )
        {
            return;
        }
        else
        {
            if  ( "asc".equalsIgnoreCase(i_FiledValue) )
            {
                this.setFieldValue(ORDER.asc);
            }
            else
            {
                this.setFieldValue(ORDER.desc);
            }
        }
    }
    
    
    
    public SFieldSort(String i_FieldName ,ORDER i_FiledValue)
    {
        super(i_FieldName ,i_FiledValue);
    }
    
    
    
    @Override
    public void setFieldValue(ORDER i_FieldValue)
    {
        if ( i_FieldValue == null )
        {
            this.fieldValue = ORDER.desc;
        }
        else
        {
            this.fieldValue = i_FieldValue;
        }
    }

    
    
    @Override
    public String toString()
    {
        if ( this.fieldName == null )
        {
            return "";
        }
        else if ( this.fieldValue == null )
        {
            return this.fieldName + " " + ESortType.$Desc.getValue();
        }
        else
        {
            return this.fieldName + " " + (this.fieldValue == ORDER.desc ? "desc" : "asc");
        }
    }
    
}
