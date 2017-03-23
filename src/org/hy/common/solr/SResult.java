package org.hy.common.solr;

import java.util.List;

import org.apache.solr.client.solrj.response.FacetField.Count;

import org.hy.common.Date;
import org.hy.common.TablePartition;





/**
 * Solr查询结果信息
 *
 * @author      ZhengWei(HY)
 * @createDate  2014-12-09
 * @version     v1.0
 */
public class SResult<O>
{
    
    /** 查询结果对于的查询条件 */
    private SQuery                               sQuery;
    
    /** 搜索开始时间 */
    private Date                                 beginTime;
    
    /** 搜索总耗时(单位为毫秒) */
    private int                                  qtime;
    
    /** 搜索到记录总数 */
    private long                                 numFound;
    
    /** 搜索结果数据 */
    private List<O>                              datas;
    
    /** 统计结果数据 */
    private TablePartition<String ,Count>        facetDatas;
    
    /** 高亮结果数据 */
    private List<Object>                         HighlightDatas;
    
    
    
    /**
     * 是否启用统计功能
     * 
     * @return
     */
    public boolean isFacet()
    {
        return this.sQuery != null && this.sQuery.isAllowFacet();  
    }
    
    
    
    /**
     * 是否启用高亮功能
     * 
     * @return
     */
    public boolean isHighlight()
    {
        return this.sQuery != null && this.sQuery.isAllowHighlight();
    }
    
    
    
    /**
     * 获取：查询结果对于的查询条件
     */
    public SQuery getSQuery()
    {
        return sQuery;
    }

    
    
    /**
     * 设置：查询结果对于的查询条件
     * 
     * @param sQuery 
     */
    public void setSQuery(SQuery sQuery)
    {
        this.sQuery = sQuery;
    }

    

    /**
     * 获取：搜索开始时间
     */
    public Date getBeginTime()
    {
        return beginTime;
    }
    

    
    /**
     * 设置：搜索开始时间
     * 
     * @param beginTime 
     */
    public void setBeginTime(Date beginTime)
    {
        this.beginTime = beginTime;
    }


    
    /**
     * 获取：搜索总耗时(单位为毫秒)
     */
    public int getQtime()
    {
        return qtime;
    }


    
    /**
     * 设置：搜索总耗时(单位为毫秒)
     * 
     * @param qtime 
     */
    public void setQtime(int qtime)
    {
        this.qtime = qtime;
    }


    
    /**
     * 获取：搜索到记录总数
     */
    public long getNumFound()
    {
        return numFound;
    }

    

    /**
     * 设置：搜索到记录总数
     * 
     * @param numFound 
     */
    public void setNumFound(long numFound)
    {
        this.numFound = numFound;
    }


    
    /**
     * 获取：搜索结果数据
     */
    public List<O> getDatas()
    {
        return datas;
    }


    
    /**
     * 设置：搜索结果数据
     * 
     * @param datas 
     */
    public void setDatas(List<O> datas)
    {
        this.datas = datas;
    }


    
    /**
     * 获取：统计结果数据
     */
    public TablePartition<String ,Count> getFacetDatas()
    {
        return facetDatas;
    }


    
    /**
     * 设置：统计结果数据
     * 
     * @param facetDatas 
     */
    public void setFacetDatas(TablePartition<String ,Count> facetDatas)
    {
        this.facetDatas = facetDatas;
    }


    
    /**
     * 获取：高亮结果数据
     */
    public List<Object> getHighlightDatas()
    {
        return HighlightDatas;
    }


    
    /**
     * 设置：高亮结果数据
     * 
     * @param highlightDatas 
     */
    public void setHighlightDatas(List<Object> highlightDatas)
    {
        HighlightDatas = highlightDatas;
    }
    
}
