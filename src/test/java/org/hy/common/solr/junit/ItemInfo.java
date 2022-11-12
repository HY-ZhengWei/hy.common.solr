package org.hy.common.solr.junit;

import org.apache.solr.client.solrj.beans.Field;

import org.hy.common.xml.SerializableDef;





/**
 * 用于Solr查询测试
 *
 * @author      ZhengWei(HY)
 * @createDate  2014-12-09
 * @version     v1.0
 */
public class ItemInfo extends SerializableDef
{
    
    private static final long serialVersionUID = 2325415315172202822L;

    @Field
    private String id;
    
    @Field
    private String commoNo;
    
    private String title;

    
    
    public String getId()
    {
        return id;
    }

    
    public void setId(String id)
    {
        this.id = id;
    }

    
    public String getCommoNo()
    {
        return commoNo;
    }

    
    public void setCommoNo(String commoNo)
    {
        this.commoNo = commoNo;
    }

    
    public String getTitle()
    {
        return title;
    }

    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
}
