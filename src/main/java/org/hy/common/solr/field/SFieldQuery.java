package org.hy.common.solr.field;

import org.hy.common.Help;
import org.hy.common.StringHelp;
import org.hy.common.solr.SField;





/**
 * Solr的查询条件字段信息 -- 对应命令：q
 * 
 * @author      ZhengWei(HY)
 * @createDate  2014-12-06
 * @version     v1.0
 */
public class SFieldQuery extends SField<String>
{
    
    private static final long serialVersionUID = 5178753192813070250L;

    
    
    public SFieldQuery()
    {
        this(null ,null);
    }
    
    
    
    public SFieldQuery(String i_FieldName)
    {
        this(i_FieldName ,null);
    }
    
    
    
    public SFieldQuery(String i_FieldName ,String i_FiledValue)
    {
        super(i_FieldName ,i_FiledValue);
    }
    
    
    
    @Override
    public void setFieldValue(String i_FieldValue)
    {
        if ( !Help.isNull(i_FieldValue) )
        {
            if ( "*:*".equals(i_FieldValue.trim()) )
            {
                this.fieldValue = "*:*";
            }
            else
            {
                this.fieldValue = StringHelp.removeSpaces(i_FieldValue.trim());
            }
        }
        else
        {
            this.fieldValue = null;
        }
    }

    
    
    @Override
    public String toString()
    {
        if ( Help.isNull(this.fieldName) )
        {
            if ( Help.isNull(this.fieldValue) )
            {
                // 搜索所有
                return $Default_QueryAll + $Default_Split + $Default_QueryAll;
            }
            else
            {
                // 要求Solr服务本身配置(或启用)了默认搜索字段，否则无效
                // 或者是用户自己定义好的，按Solr格式写的 q 命令
                return this.fieldValue;
            }
        }
        else
        {
            if ( Help.isNull(this.fieldValue) )
            {
                return this.fieldName + $Default_Split + $Default_QueryAll;
            }
            else
            {
                return this.fieldName + $Default_Split + this.fieldValue;
            }
        }
    }
    
}
