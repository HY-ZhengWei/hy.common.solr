package org.hy.common.solr.enums;

import org.hy.common.Help;





/**
 * Solr查询排序类型
 *
 * @author      ZhengWei(HY)
 * @createDate  2014-12-05
 * @version     v1.0
 */
public enum ESortType
{
    
    /** 降序。排序类型(Solr默认为：降序) */
    $Desc("desc"),
    
    /** 升序 */
    $Asc("asc");
    
    
    
    private String value;
    
    
    
    public static ESortType get(String i_Value)
    {
        if ( Help.isNull(i_Value) )
        {
            return null;
        }
        
        String v_Value = i_Value.trim();
        
        for (ESortType v_Enum : ESortType.values()) 
        {
            if ( v_Enum.value.equalsIgnoreCase(v_Value) ) 
            {
                return v_Enum;
            }
        }
        
        return null;
    }
    
    
    
    ESortType(String i_Value)
    {
        this.value = i_Value;
    }

    
    
    public String getValue()
    {
        return this.value;
    }
    
    
    
    /**
     * 反向交换
     * 
     * @return
     */
    public ESortType reverse() 
    {
        return (this == $Asc) ? $Desc : $Asc;
    }
    
}
