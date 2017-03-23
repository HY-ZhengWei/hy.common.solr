package org.hy.common.solr;

import org.hy.common.xml.SerializableDef;





/**
 * Solr分页信息
 *
 * @author      ZhengWei(HY)
 * @createDate  2014-12-05
 * @version     v1.0
 */
public class SPage extends SerializableDef implements Cloneable
{
    
    private static final long serialVersionUID = -3027664870916499239L;
    

    /** 默认的每页记录数 */
    public static final int   $Default_PerPageSize = 10;
    
    /** 最小开始页码 */
    public static final int   $Min_PageNo          = 1;
    
    
    /** 页码。下标从 1 开始 */
    private int               pageNo;

    /** 每页分页数 */
    private int               perPageSize;
 
    
    
    public SPage()
    {
        this($Default_PerPageSize);
    }
    
    
    
    public SPage(int i_PerPageSize)
    {
        this(i_PerPageSize ,$Min_PageNo);
    }
    
    
    
    public SPage(int i_PerPageSize ,int i_PageNo)
    {
        this.setPerPageSize(i_PerPageSize);
        this.setPageNo(     i_PageNo);
    }
    
    
    
    /**
     * 获取从第几个数据开始返回查询结果
     * 
     * @author      ZhengWei(HY)
     * @createDate  2014-12-08
     * @version     v1.0
     *
     * @return
     */
    public int getStartRow()
    {
        return (this.pageNo - 1) * this.perPageSize;
    }
    
    
    
    /**
     * 获取：页码。下标从 1 开始
     */
    public int getPageNo()
    {
        return pageNo;
    }
    

    
    /**
     * 设置：页码。下标从 1 开始
     * 
     * @param i_PageNo 
     */
    public void setPageNo(int i_PageNo)
    {
        if ( i_PageNo < $Min_PageNo )
        {
            this.pageNo = $Min_PageNo;
        }
        else
        {
            this.pageNo = i_PageNo;
        }
    }


    
    /**
     * 获取：每页分页数
     */
    public int getPerPageSize()
    {
        return perPageSize;
    }

    
    
    /**
     * 设置：每页分页数
     * 
     * @param i_PerPageSize 
     */
    public void setPerPageSize(int i_PerPageSize)
    {
        if ( i_PerPageSize <= 0 )
        {
            this.perPageSize = $Default_PerPageSize;
        }
        else
        {
            this.perPageSize = i_PerPageSize;
        }
    }



    @Override
    public SPage clone()
    {
        SPage v_Clone = new SPage();
        
        super.clone(v_Clone);
        
        return v_Clone;
    }
    
}
