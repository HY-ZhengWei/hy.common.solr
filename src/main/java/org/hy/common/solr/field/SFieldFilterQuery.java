package org.hy.common.solr.field;

import org.hy.common.Help;
import org.hy.common.solr.SField;





/**
 * Solr的查询条件字段信息 -- 对应命令：fq
 * 
 * @author      ZhengWei(HY)
 * @createDate  2014-12-06
 * @version     v1.0
 */
public class SFieldFilterQuery extends SField<String>
{
    
    private static final long serialVersionUID = -5030182666134542104L;



    public SFieldFilterQuery()
    {
        this(null ,null);
    }
    
    
    
    public SFieldFilterQuery(String i_FieldName)
    {
        this(i_FieldName ,null);
    }
    
    
    
    public SFieldFilterQuery(String i_FieldName ,String i_FiledValue)
    {
        super(i_FieldName ,i_FiledValue);
    }
    
    
    
    @Override
    public void setFieldValue(String i_FieldValue)
    {
        if ( Help.isNull(i_FieldValue) )
        {
            this.fieldValue = $Default_QueryAll;
        }
        else
        {
            this.fieldValue = i_FieldValue.trim();
        }
    }
    
    
    
    @Override
    public String toString()
    {
        if ( this.fieldName == null || this.fieldValue == null )
        {
            return "";
        }
        else if ( $Default_QueryAll.equals(this.fieldName) )
        {
            return "";
        }
        else if ( $Default_QueryAll.equals(this.fieldValue) )
        {
            return "";
        }
        else
        {
            return this.fieldName + $Default_Split + this.fieldValue;
        }
    }
    
}
