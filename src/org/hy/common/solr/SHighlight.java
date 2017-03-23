package org.hy.common.solr;

import java.util.ArrayList;
import java.util.List;

import org.hy.common.Help;
import org.hy.common.xml.SerializableDef;




/**
 * Solr高度查询条件
 *
 * @author      ZhengWei(HY)
 * @createDate  2014-12-11
 * @version     v1.0
 */
public class SHighlight extends SerializableDef
{
    
    private static final long serialVersionUID = -475868003426772476L;
    

    /** 高度字段 */
    private List<String>               fields;
    
    /** 高亮前面的格式 */
    private String                     simplePre;
    
    /** 高亮后面的格式 */
    private String                     simplePost;

    
    
    public SHighlight()
    {
        
    }
    
    
    
    public SHighlight(String i_FieldNames)
    {
        this.setField(i_FieldNames);
    }
    
    
    
    public SHighlight(List<String> i_FieldNames)
    {
        this.fields = i_FieldNames;
    }
    
    
    
    /**
     * 是否启用高亮功能
     * 
     * @return
     */
    public boolean isAllowHighlight()
    {
        return !Help.isNull(this.fields);
    }
    
    
    
    /**
     * 别名：可与Solr的命名看齐
     * 
     * @param i_FieldName
     */
    public synchronized void setFl(String i_FieldNames)
    {
        this.setField(i_FieldNames);
    }
    
    
    
    /**
     * 逗号分隔的字段字符串。如 fname01,fname02...fname0n
     * 
     * @author      ZhengWei(HY)
     * @createDate  2014-12-11
     * @version     v1.0
     *
     * @param i_FieldNames
     */
    public synchronized void setField(String i_FieldNames)
    {
        if ( !Help.isNull(i_FieldNames) )
        {
            String [] v_FieldArr = i_FieldNames.split(",");
            
            if ( this.fields == null )
            {
                this.fields = new ArrayList<String>();
            }
            
            for (String v_FieldName : v_FieldArr)
            {
                if ( !Help.isNull(v_FieldName) )
                {
                    this.fields.add(v_FieldName.trim());
                }
            }
        }
    }
    
    
    
    /**
     * 获取：高度字段
     */
    public List<String> getFields()
    {
        return fields;
    }

    
    
    /**
     * 设置：高度字段
     * 
     * @param fields 
     */
    public void setFields(List<String> fields)
    {
        this.fields = fields;
    }

    
    
    /**
     * 获取：高亮前面的格式
     */
    public String getSimplePre()
    {
        return simplePre;
    }

    
    
    /**
     * 设置：高亮前面的格式
     * 
     * @param i_SimplePre 
     */
    public void setSimplePre(String i_SimplePre)
    {
        if ( i_SimplePre == null )
        {
            this.simplePre = null;
        }
        else
        {
            this.simplePre = i_SimplePre.trim();
        }
    }

    
    
    /**
     * 获取：高亮后面的格式
     */
    public String getSimplePost()
    {
        return simplePost;
    }

    
    
    /**
     * 设置：高亮后面的格式
     * 
     * @param i_SimplePost 
     */
    public void setSimplePost(String i_SimplePost)
    {
        if ( i_SimplePost == null )
        {
            this.simplePost = null;
        }
        else
        {
            this.simplePost = i_SimplePost.trim();
        }
    }
    
}
