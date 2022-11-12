package org.hy.common.solr.field;

import org.hy.common.Help;
import org.hy.common.solr.SField;





/**
 * Solr的查询条件字段信息 -- 对应命令：facet.prefix
 * 
 * @author      ZhengWei(HY)
 * @createDate  2014-12-10
 * @version     v1.0
 */
public class SFieldFacetPrefix extends SField<String>
{
    
    private static final long serialVersionUID = -3660488350625849526L;



    public SFieldFacetPrefix()
    {
        this(null ,null);
    }
    
    
    
    public SFieldFacetPrefix(String i_FieldName)
    {
        this(i_FieldName ,null);
    }
    
    
    
    public SFieldFacetPrefix(String i_FieldName ,String i_FiledValue)
    {
        super(i_FieldName ,i_FiledValue);
    }
    
    
    
    /**
     * 起个别名
     * 
     * @param i_Prefix
     */
    public void setPrefix(String i_Prefix)
    {
        this.setFieldValue(i_Prefix);
    }
    
    
    
    /**
     * 起个别名
     * 
     * @param i_Prefix
     */
    public String getPrefix()
    {
        return this.getFieldValue();
    }
    
    
    
    @Override
    public void setFieldValue(String i_FieldValue)
    {
        if ( Help.isNull(i_FieldValue) )
        {
            this.fieldValue = null;
        }
        else
        {
            this.fieldValue = i_FieldValue.trim();
        }
    }

    
    
    @Override
    public String toString()
    {
        if ( Help.isNull(this.fieldValue) )
        {
            return "";
        }
        else
        {
            return this.fieldValue;
        }
    }
    
}
