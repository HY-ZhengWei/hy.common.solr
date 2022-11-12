package org.hy.common.solr;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;

import org.hy.common.Help;
import org.hy.common.StringHelp;
import org.hy.common.solr.field.SFieldFilterQuery;
import org.hy.common.solr.field.SFieldQuery;
import org.hy.common.solr.field.SFieldSort;
import org.hy.common.xml.SerializableDef;





/**
 * Solr查询条件信息
 * 
 * 默认情况下是有分页的
 *
 * @author      ZhengWei(HY)
 * @createDate  2014-12-05
 * @version     v1.0
 */
public class SQuery extends SerializableDef implements Comparable<SQuery> ,Cloneable
{
    
    private static final long serialVersionUID = 7095925709551185069L;
    

    /** 唯一标示，主用于对比等操作 */
    private String                               uuid;
    
    /** 搜索词 */
    private SFieldQuery                          queryWord; 
    
    /** 过虑查询 */
    private List<SFieldFilterQuery>              filterQuerys;

    /** 排序字段 */
    private List<SFieldSort>                     sortFields;
    
    /** Solr查询'平面'的统计  */
    private SFacet                               facet;
    
    /** 分页信息。当为空时，表示不分页，不获取数据，只获取记录总数 */
    private SPage                                page;
    
    /** 指定要显示的字段，默认显示全部字段 */
    private List<String>                         selectFields;
    
    /** 高亮显示 */
    private SHighlight                           highlight;

    
    
    public SQuery()
    {
        this(new SFieldQuery());
    }
    
    
    
    public SQuery(String i_QueryWord)
    {
        this(new SFieldQuery(null ,i_QueryWord));
    }
    
    
    
    public SQuery(SPage i_PageInfo)
    {
        this(new SFieldQuery() ,(List<SFieldFilterQuery>)null ,i_PageInfo);
    }
    
    
    
    public SQuery(List<SFieldFilterQuery> i_FilterQuery)
    {
        this(new SFieldQuery() ,i_FilterQuery ,new SPage());
    }
    
    
    
    public SQuery(String i_QueryWord ,SPage i_PageInfo)
    {
        this(new SFieldQuery(null ,i_QueryWord) ,(List<SFieldFilterQuery>)null ,i_PageInfo);
    }
    
    
    
    public SQuery(String i_QueryWord ,String i_FilterQuery)
    {
        this(new SFieldQuery(null ,i_QueryWord) ,new SFieldFilterQuery(i_FilterQuery) ,new SPage());
    }
    
    
    
    public SQuery(String i_QueryWord ,SFieldFilterQuery i_FilterQuery)
    {
        this(new SFieldQuery(null ,i_QueryWord) ,i_FilterQuery ,new SPage());
    }
    
    
    
    public SQuery(String i_QueryWord ,List<SFieldFilterQuery> i_FilterQuery)
    {
        this(new SFieldQuery(null ,i_QueryWord) ,i_FilterQuery ,new SPage());
    }
    
    
    
    public SQuery(String i_QueryWord ,String i_FilterQuery ,SPage i_PageInfo)
    {
        this(new SFieldQuery(null ,i_QueryWord) ,new SFieldFilterQuery(i_FilterQuery) ,i_PageInfo);
    }
    
    
    
    public SQuery(String i_QueryWord ,SFieldFilterQuery i_FilterQuery ,SPage i_PageInfo)
    {
        this(new SFieldQuery(null ,i_QueryWord) ,i_FilterQuery ,i_PageInfo);
    }
    
    
    
    public SQuery(String i_QueryWord ,List<SFieldFilterQuery> i_FilterQuery ,SPage i_PageInfo)
    {
        this(new SFieldQuery(null ,i_QueryWord) ,i_FilterQuery ,i_PageInfo);
    }
    
    
    
    public SQuery(SFieldQuery i_QueryWord)
    {
        this(i_QueryWord ,(List<SFieldFilterQuery>)null ,new SPage());
    }
    
    
    
    public SQuery(SFieldQuery i_QueryWord ,String i_FilterQuery)
    {
        this(i_QueryWord ,new SFieldFilterQuery(i_FilterQuery) ,new SPage());
    }
    
    
    
    public SQuery(SFieldQuery i_QueryWord ,SFieldFilterQuery i_FilterQuery)
    {
        this(i_QueryWord ,i_FilterQuery ,new SPage());
    }
    
    
    
    public SQuery(SFieldQuery i_QueryWord ,List<SFieldFilterQuery> i_FilterQuerys)
    {
        this(i_QueryWord ,i_FilterQuerys ,new SPage());
    }
    
    
    
    public SQuery(SFieldQuery i_QueryWord ,SPage i_PageInfo)
    {
        this(i_QueryWord ,(List<SFieldFilterQuery>)null ,i_PageInfo);
    }
    
    
    
    public SQuery(SFieldQuery i_QueryWord ,SFieldFilterQuery i_FilterQuery ,SPage i_PageInfo)
    {
        this(i_QueryWord ,(List<SFieldFilterQuery>)null ,i_PageInfo);
        this.addFilterQuery(i_FilterQuery);
    }
    
    
    
    public SQuery(SFieldQuery i_QueryWord ,List<SFieldFilterQuery> i_FilterQuerys ,SPage i_PageInfo)
    {
        this.uuid = StringHelp.getUUID();
        this.setQueryWord(   i_QueryWord);
        this.setFilterQuerys(i_FilterQuerys);
        this.setPage(    i_PageInfo);
    }
    
    
    
    /**
     * 转变为Solr查询对象
     * 
     * @author      ZhengWei(HY)
     * @createDate  2014-12-08
     * @version     v1.0
     *
     * @return
     */
    public SolrQuery toSolrQuery()
    {
        SolrQuery v_SolrQuery = new SolrQuery();
        
        if ( this.queryWord != null )
        {
            v_SolrQuery.setQuery(this.queryWord.toString());
        }
        else
        {
            v_SolrQuery.setQuery("*:*");
        }
        
        if ( !Help.isNull(this.filterQuerys) )
        {
            for (SFieldFilterQuery v_SFilterQuery : this.filterQuerys)
            {
                v_SolrQuery.addFilterQuery(v_SFilterQuery.toString());
            }
        }
        
        if ( !Help.isNull(this.sortFields) )
        {
            for (SFieldSort v_SSort : this.sortFields)
            {
                if ( !Help.isNull(v_SSort.getFieldName()) )
                {
                    v_SolrQuery.addSort(v_SSort.getFieldName() ,v_SSort.getFieldValue());
                }
            }
        }
        
        if ( this.isAllowFacet() )
        {
            v_SolrQuery.setFacet(true);
            
            for (String v_FacetFieldName : this.facet.getFields())
            {
                if ( !Help.isNull(v_FacetFieldName) )
                {
                    v_SolrQuery.addFacetField(v_FacetFieldName);
                }
            }
            
            if ( this.facet.getPrefixInfo() != null )
            {
                if ( !Help.isNull(this.facet.getPrefixInfo().getFieldName()) 
                  && !Help.isNull(this.facet.getPrefixInfo().getFieldValue()) )
                {
                    v_SolrQuery.setFacetPrefix(this.facet.getPrefixInfo().getFieldName() ,this.facet.getPrefixInfo().getFieldValue());
                }
                else if ( !Help.isNull(this.facet.getPrefixInfo().getFieldValue()) )
                {
                    v_SolrQuery.setFacetPrefix(this.facet.getPrefixInfo().getFieldValue());
                }
            }
            
            if ( !Help.isNull(this.facet.getQuery()) )
            {
                v_SolrQuery.setQuery(this.facet.getQuery());
            }
            
            if ( this.facet.getLimit() != null )
            {
                v_SolrQuery.setFacetLimit(this.facet.getLimit());
            }
        }
        
        if ( this.page == null )
        {
            v_SolrQuery.setRows(0);
        }
        else
        {
            v_SolrQuery.setStart(this.page.getStartRow());
            v_SolrQuery.setRows( this.page.getPerPageSize());
        }
        
        if ( !Help.isNull(this.selectFields) )
        {
            for (String i_FieldName : this.selectFields)
            {
                if ( !Help.isNull(i_FieldName) )
                {
                    v_SolrQuery.addField(i_FieldName.trim());
                }
            }
        }
        
        if ( this.isAllowHighlight() )
        {
            v_SolrQuery.setHighlight(true);
            
            for (String v_HFieldName : this.highlight.getFields())
            {
                if ( !Help.isNull(v_HFieldName) )
                {
                    v_SolrQuery.addHighlightField(v_HFieldName);
                }   
            }
            
            if ( !Help.isNull(this.highlight.getSimplePre()) )
            {
                v_SolrQuery.setHighlightSimplePre(this.highlight.getSimplePre());
            }
            
            if ( !Help.isNull(this.highlight.getSimplePost()) )
            {
                v_SolrQuery.setHighlightSimplePost(this.highlight.getSimplePost());
            }
        }
        
        return v_SolrQuery;
    }
    
    
    
    /**
     * 获取：搜索词
     */
    public SFieldQuery getQueryWord()
    {
        return queryWord;
    }
    
    
    
    /**
     * 设置：搜索词
     * 
     * @param queryWord 
     */
    public void setQ(String i_QueryWord)
    {
        this.queryWord = new SFieldQuery(null ,i_QueryWord);
    }

    
    
    /**
     * 设置：搜索词
     * 
     * @param queryWord 
     */
    public void setQueryWord(SFieldQuery i_QueryWord)
    {
        this.queryWord = i_QueryWord;
    }

    
    
    /**
     * 获取：过虑查询
     */
    public List<SFieldFilterQuery> getFilterQuerys()
    {
        return filterQuerys;
    }


    
    /**
     * 设置：过虑查询
     * 
     * @param filterQuerys 
     */
    public void setFilterQuerys(List<SFieldFilterQuery> i_FilterQuerys)
    {
        if ( !Help.isNull(i_FilterQuerys) )
        {
            for (int i=0; i<i_FilterQuerys.size(); i++)
            {
                if ( Help.isNull(i_FilterQuerys.get(i).getFieldName()) )
                {
                    throw new NullPointerException("FilterQuery[" + i + "] is null.");
                }
            }
        }
        
        this.filterQuerys = i_FilterQuerys;
    }
    
    
    
    /**
     * 别名：可与Solr的命名看齐
     * 
     * @param i_FilterQuery
     */
    public synchronized void setFq(SFieldFilterQuery i_FilterQuery)
    {
        this.addFilterQuery(i_FilterQuery);
    }
    
    
    
    /**
     * 添加一个过滤字段
     * 
     * @author      ZhengWei(HY)
     * @createDate  2014-12-05
     * @version     v1.0
     *
     * @param i_FilterQuery
     */
    public synchronized void setAddFilterQuery(SFieldFilterQuery i_FilterQuery)
    {
        this.addFilterQuery(i_FilterQuery);
    }
    
    
    
    /**
     * 添加一个过滤字段
     * 
     * @author      ZhengWei(HY)
     * @createDate  2014-12-11
     * @version     v1.0
     *
     * @param i_FilterQuery
     */
    public synchronized void addFilterQuery(String i_FilterQuery)
    {
        this.addFilterQuery(new SFieldFilterQuery(i_FilterQuery));
    }
    
    
    
    /**
     * 添加一个过滤字段
     * 
     * @author      ZhengWei(HY)
     * @createDate  2014-12-11
     * @version     v1.0
     *
     * @param i_FilterQuery
     */
    public synchronized void addFilterQuery(String i_FilterQueryName ,String i_FilterQueryValue)
    {
        this.addFilterQuery(new SFieldFilterQuery(i_FilterQueryName ,i_FilterQueryValue));
    }
    
    
    
    /**
     * 添加一个过滤字段
     * 
     * @author      ZhengWei(HY)
     * @createDate  2014-12-05
     * @version     v1.0
     *
     * @param i_FilterQuery
     */
    public synchronized void addFilterQuery(SFieldFilterQuery i_FilterQuery)
    {
        if ( i_FilterQuery == null )
        {
            return;
        }
        
        if ( Help.isNull(i_FilterQuery.getFieldName()) )
        {
            throw new NullPointerException("Sort Field is null.");
        }
        
        if ( this.filterQuerys == null )
        {
            this.filterQuerys = new ArrayList<SFieldFilterQuery>();
        }
        
        this.filterQuerys.add(i_FilterQuery);
    }



    /**
     * 获取：排序字段
     */
    public List<SFieldSort> getSortFields()
    {
        return sortFields;
    }

    
    
    /**
     * 设置：排序字段
     * 
     * @param i_SortFields 
     */
    public void setSortFields(List<SFieldSort> i_SortFields)
    {
        if ( !Help.isNull(i_SortFields) )
        {
            for (int i=0; i<i_SortFields.size(); i++)
            {
                if ( Help.isNull(i_SortFields.get(i).getFieldName()) )
                {
                    throw new NullPointerException("Sort Field[" + i + "] is null.");
                }
            }
        }
        
        this.sortFields = i_SortFields;
    }
    
    
    
    public synchronized void setSort(String i_SortInfo)
    {
        if ( Help.isNull(i_SortInfo) )
        {
            return;
        }
        
        String [] v_SortArr = i_SortInfo.trim().split(",");
        
        for (String v_Sort : v_SortArr)
        {
            if ( !Help.isNull(v_Sort) )
            {
                String [] v_SortFO = StringHelp.removeSpaces(v_Sort.trim()).split(" ");
                
                if ( v_SortFO.length == 1 )
                {
                    this.addSort(new SFieldSort(v_SortFO[0]));
                }
                else
                {
                    this.addSort(new SFieldSort(v_SortFO[0] ,v_SortFO[1]));
                }
            }
        }
    }
    
    
    
    /**
     * 添加一个排序字段
     * 
     * @author      ZhengWei(HY)
     * @createDate  2014-12-05
     * @version     v1.0
     *
     * @param i_SFieldSort
     */
    public synchronized void setAddSort(SFieldSort i_SFieldSort)
    {
        this.addSort(i_SFieldSort);
    }
    
    
    
    /**
     * 添加一个排序字段
     * 
     * @author      ZhengWei(HY)
     * @createDate  2014-12-05
     * @version     v1.0
     *
     * @param i_SFieldSort
     */
    public synchronized void addSort(SFieldSort i_SFieldSort)
    {
        if ( i_SFieldSort == null )
        {
            return;
        }
        
        if ( Help.isNull(i_SFieldSort.getFieldName()) )
        {
            throw new NullPointerException("Sort Field is null.");
        }
        
        if ( this.sortFields == null )
        {
            this.sortFields = new ArrayList<SFieldSort>();
        }
        
        this.sortFields.add(i_SFieldSort);
    }
    
    
    
    /**
     * 获取：Solr查询'平面'的统计
     */
    public SFacet getFacet()
    {
        return facet;
    }
    
    
    
    /**
     * 设置：Solr查询'平面'的统计
     * 
     * @param facet 
     */
    public void setFacetField(String i_FacetField)
    {
        this.facet = new SFacet(i_FacetField);
    }


    
    /**
     * 设置：Solr查询'平面'的统计
     * 
     * @param facet 
     */
    public void setFacet(SFacet facet)
    {
        this.facet = facet;
    }
    
    
    
    /**
     * 是否启用统计功能
     * 
     * @return
     */
    public boolean isAllowFacet()
    {
        return this.facet != null && this.facet.isAllowFacet();  
    }
    
    
    
    /**
     * 是否启用高亮功能
     * 
     * @return
     */
    public boolean isAllowHighlight()
    {
        return this.highlight != null && this.highlight.isAllowHighlight();  
    }



    /**
     * 获取：分页信息。当为空时，表示不分页，不获取数据，只获取记录总数
     */
    public SPage getPage()
    {
        return page;
    }


    
    /**
     * 设置：分页信息。当为空时，表示不分页，不获取数据，只获取记录总数
     * 
     * @param i_Page 
     */
    public void setPage(SPage i_Page)
    {
        if ( i_Page == null )
        {
            this.page = null;
        }
        else
        {
            this.page = i_Page;
        }
    }
    
    
    
    /**
     * 别名：可与Solr的命名看齐
     * 
     * @param i_FieldName
     */
    public synchronized void setFl(String i_FieldName)
    {
        this.setSelectField(i_FieldName);
    }
    
    
    
    /**
     * 逗号分隔的字段字符串。如 fname01,fname02...fname0n
     * 
     * @author      ZhengWei(HY)
     * @createDate  2014-12-11
     * @version     v1.0
     *
     * @param i_SelectFields
     */
    public synchronized void setSelectField(String i_SelectFields)
    {
        if ( !Help.isNull(i_SelectFields) )
        {
            String [] v_FieldArr = i_SelectFields.split(",");
            
            if ( this.selectFields == null )
            {
                this.selectFields = new ArrayList<String>();
            }
            
            for (String v_FieldName : v_FieldArr)
            {
                if ( !Help.isNull(v_FieldName) )
                {
                    this.selectFields.add(v_FieldName.trim());
                }
            }
        }
    }
    
    
    
    /**
     * 添加要显示的字段
     * 
     * @author      ZhengWei(HY)
     * @createDate  2014-12-11
     * @version     v1.0
     *
     * @param i_FieldName
     */
    public synchronized void setAddSelectField(String i_FieldName)
    {
        this.addSelectField(i_FieldName);
    }
    
    
    
    /**
     * 添加要显示的字段
     * 
     * @author      ZhengWei(HY)
     * @createDate  2014-12-11
     * @version     v1.0
     *
     * @param i_FieldName
     */
    public synchronized void addSelectField(String i_FieldName)
    {
        if ( Help.isNull(i_FieldName) )
        {
            return;
        }
        
        if ( this.selectFields == null )
        {
            this.selectFields = new ArrayList<String>();
        }
        
        this.selectFields.add(i_FieldName.trim());
    }
    
    
    
    /**
     * 获取：指定要显示的字段，默认显示全部字段
     */
    public List<String> getSelectFields()
    {
        return selectFields;
    }


    
    /**
     * 设置：指定要显示的字段，默认显示全部字段
     * 
     * @param selectFields 
     */
    public void setSelectFields(List<String> selectFields)
    {
        this.selectFields = selectFields;
    }
    
    
    
    /**
     * 获取：高亮显示
     */
    public SHighlight getHighlight()
    {
        return highlight;
    }


    
    /**
     * 设置：高亮显示
     * 
     * @param highlight 
     */
    public void setHighlight(SHighlight i_Highlight)
    {
        this.highlight = i_Highlight;
    }

    
    
    /**
     * 别名：可与Solr的命名看齐
     * 
     * @param highlight 
     */
    public void setHl(SHighlight i_Highlight)
    {
        this.highlight = i_Highlight;
    }

    

    public String getObjectID()
    {
        return this.uuid;
    }
    
    
    
    @Override
    public int hashCode()
    {
        return this.getObjectID().hashCode();
    }
    
    
    
    @Override
    public boolean equals(Object i_Other)
    {
        if ( i_Other == null )
        {
            return false;
        }
        else if ( this == i_Other )
        {
            return true;
        }
        else if ( i_Other instanceof SQuery )
        {
            return this.getObjectID().equals(((SQuery)i_Other).getObjectID());
        }
        else
        {
            return false;
        }
    }
    
    
    
    public int compareTo(SQuery i_SQuery)
    {
        if ( i_SQuery == null )
        {
            return 1;
        }
        else if ( this == i_SQuery )
        {
            return 0;
        }
        else
        {
            return this.getObjectID().compareTo(i_SQuery.getObjectID());
        }
    }

    

    @Override
    public SQuery clone()
    {
        SQuery v_Clone = new SQuery();
        
        super.clone(v_Clone);
        
        return v_Clone;
    }



    @Override
    public String toString()
    {
        return this.toSolrQuery().toString();
    }
    
}
