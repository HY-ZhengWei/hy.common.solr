package org.hy.common.solr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.hy.common.Date;
import org.hy.common.Help;
import org.hy.common.MethodReflect;
import org.hy.common.TablePartition;
import org.hy.common.solr.field.SFieldQuery;
import org.hy.common.xml.SerializableDef;





/**
 * Solr搜索服务访问类
 *          
 * @author ZhengWei(HY)
 * @create 2014-11-03
 */
public class Solr
{
    
    /** lucene的转义字符（及转义词） 用空格分割 */
    public static String LUCENE_ESCAPE_CHARACTER           = "+ – && || ! ( ) { } [ ] ^ \" ~ * ? : / \\ AND OR NOT"; 
    
    /** 针对lucene转义字符（词）的正则 */
    public static String REGEX_FOR_LUCENE_ESCAPE_CHARACTER = "(\\+|-|&&|\\|\\||!|\\(|\\)|\\{|\\}|\\[|\\]|\\^|\"|\\~|\\*|\\?|:|/|\\\\|AND|OR|NOT)";
    
    
    
    /** Solr服务的http地址 */
    private String             baseURL;
    
    /** 连接超时时长(单位：毫秒) */
    private int                connTimeout;
    
    /** 每台主机连接最大数 */
    private int                maxConnPerHost;
    
    /** 所有主机连接最大数 */
    private int                maxConnTotal;
    
    /** 最大重试次数 */
    private int                maxRetries;
    
    /** 采用懒加载模式 */
    private HttpSolrServer     solrServer;
    
    private XMLResponseParser  xml;
    
    
    
    /**
     * 对lucene的预留字符做转义,例如"2+3"会转为"2\\+3"
     * 
     * @author      ZhengWei(HY)
     * @createDate  2017-01-12
     * @version     v1.0
     *
     */
    public static String to(String i_Key)
    {
        return i_Key.replaceAll(REGEX_FOR_LUCENE_ESCAPE_CHARACTER ,"\\\\$1"); 
    }
    
    
    
    public Solr(String i_BaseURL)
    {
        if ( Help.isNull(i_BaseURL) )
        {
            throw new java.lang.InstantiationError("Solr BaseURL is null.");
        }
        
        this.baseURL        = i_BaseURL;
        this.connTimeout    = 10 * 1000;
        this.maxConnPerHost = 100;
        this.maxConnTotal   = 10000;
        this.maxRetries     = 1;
    }
    
    
    
    public synchronized SolrServer getSolr()
    {
        if ( this.solrServer == null )
        {
            this.solrServer = new HttpSolrServer(           this.baseURL);
            this.solrServer.setConnectionTimeout(           this.connTimeout);
            this.solrServer.setDefaultMaxConnectionsPerHost(this.maxConnPerHost);
            this.solrServer.setMaxTotalConnections(         this.maxConnTotal);
            this.solrServer.setMaxRetries(                  this.maxRetries);
            
            this.xml = new XMLResponseParser();
            this.solrServer.setParser(this.xml);
        }
        
        return this.solrServer;
    }
    
    
    
    public void add(Object i_Data)
    {
        if ( i_Data == null )
        {
            return;
        }
        
        SolrServer v_Server = null;
        
        try
        {
            v_Server = this.getSolr();
            if ( MethodReflect.isExtendImplement(i_Data ,SerializableDef.class) )
            {
                v_Server.add(toSolrDoc((SerializableDef)i_Data));
            }
            else
            {
                v_Server.addBean(i_Data);
            }
            v_Server.commit();
        }
        catch (Exception exce)
        {
            exce.printStackTrace();
            
            try
            {
                if ( v_Server != null )
                {
                    v_Server.rollback();
                }
            }
            catch (Exception e)
            {
                // Nothing.
            }
        }
    }
    
    
    
    public <T extends SerializableDef> void add(T i_Data)
    {
        if ( i_Data == null )
        {
            return;
        }
        
        SolrServer v_Server = null;
        
        try
        {
            v_Server = this.getSolr();
            v_Server.add(toSolrDoc(i_Data));
            v_Server.commit();
        }
        catch (Exception exce)
        {
            exce.printStackTrace();
            
            try
            {
                if ( v_Server != null )
                {
                    v_Server.rollback();
                }
            }
            catch (Exception e)
            {
                // Nothing.
            }
        }
    }
    
    
    
    public <T extends SerializableDef> void add(List<T> i_Datas)
    {
        if ( Help.isNull(i_Datas) )
        {
            return;
        }
        
        SolrServer v_Server = null;
        
        try
        {
            v_Server = this.getSolr();
            
            for (T v_Data : i_Datas)
            {
                if ( v_Data != null )
                {
                    if ( MethodReflect.isExtendImplement(v_Data ,SerializableDef.class) )
                    {
                        v_Server.add(toSolrDoc((SerializableDef)v_Data));
                    }
                    else
                    {
                        v_Server.add(toSolrDoc(v_Data));
                    }
                }
            }
            
            v_Server.commit();
        }
        catch (Exception exce)
        {
            exce.printStackTrace();
            
            try
            {
                if ( v_Server != null )
                {
                    v_Server.rollback();
                }
            }
            catch (Exception e)
            {
                // Nothing.
            }
        }
    }
    
    
    
    public void addObj(List<Object> i_Datas)
    {
        if ( Help.isNull(i_Datas) )
        {
            return;
        }
        
        SolrServer v_Server = this.getSolr();
        
        try
        {
            for (Object v_Data : i_Datas)
            {
                if ( v_Data != null )
                {
                    v_Server.addBean(v_Data);
                }
            }
            
            v_Server.commit();
        }
        catch (Exception exce)
        {
            exce.printStackTrace();
            
            try
            {
                if ( v_Server != null )
                {
                    v_Server.rollback();
                }
            }
            catch (Exception e)
            {
                // Nothing.
            }
        }
    }
    
    
    
    public void update(String i_ID ,Object i_Data)
    {
        this.delete(i_ID);
        this.add(i_Data);
    }
    
    
    
    /**
     * 按id删除Solr中的数据
     * 
     * @author      ZhengWei(HY)
     * @createDate  2014-12-18
     * @version     v1.0
     *
     * @param i_ID
     */
    public void delete(String i_ID)
    {
        if ( i_ID == null )
        {
            return;
        }
        
        SolrServer v_Server = null;
        
        try
        {
            v_Server = this.getSolr();
            v_Server.deleteById(i_ID);
            v_Server.commit();
        }
        catch (Exception exce)
        {
            exce.printStackTrace();
            
            try
            {
                if ( v_Server != null )
                {
                    v_Server.rollback();
                }
            }
            catch (Exception e)
            {
                // Nothing.
            }
        }
    }
    
    
    
    /**
     * 按多个id删除Solr中的数据
     * 
     * @author      ZhengWei(HY)
     * @createDate  2014-12-18
     * @version     v1.0
     *
     * @param i_IDs
     */
    public void delete(List<String> i_IDs)
    {
        if ( Help.isNull(i_IDs) )
        {
            return;
        }
        
        SolrServer v_Server = null;
        
        try
        {
            v_Server = this.getSolr();
            v_Server.deleteById(i_IDs);
            v_Server.commit();
        }
        catch (Exception exce)
        {
            exce.printStackTrace();
            
            try
            {
                if ( v_Server != null )
                {
                    v_Server.rollback();
                }
            }
            catch (Exception e)
            {
                // Nothing.
            }
        }
    }
    
    
    
    /**
     * 将序列化类转为Solr的文档类。好方便写入Solr中。
     * 
     * 建议：请确保数据的 id 属性为有值
     * 
     * @author      ZhengWei(HY)
     * @createDate  2014-12-18
     * @version     v1.0
     *
     * @param i_Data
     * @return
     */
    private <T extends SerializableDef> SolrInputDocument toSolrDoc(T i_Data)
    {
        SerializableDef   v_Data     = (SerializableDef)i_Data;
        List<Integer>     v_PIndexs  = v_Data.changeValues();
        SolrInputDocument v_Ret      = new SolrInputDocument();
        
        for (int i=0; i<v_PIndexs.size(); i++)
        {
            int    v_PIndex = v_PIndexs.get(i);
            Object v_Value  = v_Data.gatPropertyValue(v_PIndex);
            if ( v_Value != null )
            {
                v_Ret.addField(v_Data.gatPropertyShortName(v_PIndex) ,v_Value);
            }
        }
        
        if ( v_Ret.size() >= 1 )
        {
            return v_Ret;
        }
        else
        {
            return null;
        }
    }
    
    
    
    /**
     * 查询所有数据
     * 
     * @return
     */
    public SResult<Map<String ,?>> query()
    {
        return this.query(new SQuery());
    }
    
    
    
    /**
     * 简单搜索某个字段上的对应的值
     *
     * @param i_FieldName
     * @param i_FieldValue
     * @return
     */
    public SResult<Map<String ,?>> query(String i_FieldName ,String i_FieldValue)
    {
        if ( Help.isNull(i_FieldName) || Help.isNull(i_FieldValue) )
        {
            return null;
        }
        
        SResult<Map<String ,?>> v_Ret    = new SResult<Map<String ,?>>();
        ModifiableSolrParams    v_Params = new ModifiableSolrParams();
        SFieldQuery             v_SFiled = new SFieldQuery(i_FieldName ,i_FieldValue);
        
        // 保留这样的查询方法，而没有改有 SQuery 的原因，就是多一种现实方法，就多一种思路
        v_Params.set("qt"               ,"/select");
        v_Params.set("q"                ,v_SFiled.toString());
        v_Params.set("spellcheck"       ,"on");
        v_Params.set("spellcheck.build" ,"true");
        v_Params.set(CommonParams.WT    ,"xml");

        try
        {
            v_Ret.setBeginTime(new Date());
            v_Ret.setSQuery(   new SQuery(v_SFiled ,(SPage)null));
            
            QueryResponse v_Response = this.getSolr().query(v_Params ,SolrRequest.METHOD.POST);
            if ( v_Response != null )
            {
                SolrDocumentList v_SolrDocs = v_Response.getResults();
                
                v_Ret.setNumFound(v_SolrDocs.getNumFound());
                v_Ret.setQtime(   v_Response.getQTime());
                v_Ret.setDatas(   this.toBean(v_SolrDocs));
            }
        }
        catch (Exception exce)
        {
            exce.printStackTrace();
        }
        
        return v_Ret;
    }
    
    
    
    /**
     * 简单搜索。
     * 
     * 要求Solr服务本身配置(或启用)了默认搜索字段，否则无效
     * 或者是用户自己定义好的，按Solr格式写的 q 命令
     * 
     * @author      ZhengWei(HY)
     * @createDate  2014-12-09
     * @version     v1.0
     *
     * @param i_FieldValue
     * @return
     */
    public SResult<Map<String ,?>> query(String i_FieldValue)
    {
        return this.query(new SQuery(i_FieldValue));
    }
    
    
    
    /**
     * 简单搜索。
     * 
     * 要求Solr服务本身配置(或启用)了默认搜索字段，否则无效
     * 或者是用户自己定义好的，按Solr格式写的 q 命令
     * 
     * @author      ZhengWei(HY)
     * @createDate  2014-12-09
     * @version     v1.0
     *
     * @param i_FieldValue
     * @return
     */
    public <T> SResult<T> query(String i_FieldValue ,Class<T> i_BeanCalss)
    {
        return this.query(new SQuery(i_FieldValue) ,i_BeanCalss);
    }
    
    
    
    /**
     * 多功能搜索
     * 
     * @author      ZhengWei(HY)
     * @createDate  2014-12-08
     * @version     v1.0
     *
     * @param i_SQuery
     * @return
     */
    public SResult<Map<String ,?>> query(SQuery i_SQuery)
    {
        if ( i_SQuery == null )
        {
            return null;
        }
        
        SResult<Map<String ,?>> v_Ret = new SResult<Map<String ,?>>();
        
        try
        {
            v_Ret.setBeginTime(new Date());
            v_Ret.setSQuery(   i_SQuery);
            
            QueryResponse v_Response = this.getSolr().query(i_SQuery.toSolrQuery() ,SolrRequest.METHOD.POST);
            
            if ( v_Response != null )
            {
                SolrDocumentList v_SolrDocs = v_Response.getResults();
                
                v_Ret.setNumFound(v_SolrDocs.getNumFound());
                v_Ret.setQtime(   v_Response.getQTime());
                v_Ret.setDatas(   this.toBean(v_SolrDocs));
                
                if ( i_SQuery.isAllowFacet() )
                {
                    v_Ret.setFacetDatas(this.getFacetDatas(v_Response.getFacetFields()));
                }
                
                if ( i_SQuery.isAllowHighlight() )
                {
                    v_Ret.setHighlightDatas(this.getHighlightDatas(v_SolrDocs ,v_Response.getHighlighting()));
                }
            }
        }
        catch (Exception exce)
        {
            exce.printStackTrace();
        }
        
        return v_Ret;
    }
    
    
    
    /**
     * 多功能搜索
     * 
     * @author      ZhengWei(HY)
     * @createDate  2014-12-09
     * @version     v1.0
     *
     * @param i_SQuery
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> SResult<T> query(SQuery i_SQuery ,Class<T> i_BeanCalss)
    {
        if ( i_SQuery == null || i_BeanCalss == null )
        {
            return null;
        }
        
        SResult<T> v_Ret = new SResult<T>();
        
        try
        {
            v_Ret.setBeginTime(new Date());
            v_Ret.setSQuery(   i_SQuery);
            
            QueryResponse v_Response = this.getSolr().query(i_SQuery.toSolrQuery() ,SolrRequest.METHOD.POST);
            
            if ( v_Response != null )
            {
                SolrDocumentList v_SolrDocs = v_Response.getResults();
                
                v_Ret.setNumFound(v_SolrDocs.getNumFound());
                v_Ret.setQtime(   v_Response.getQTime());
                
                if ( MethodReflect.isExtendImplement(i_BeanCalss ,SerializableDef.class) )
                {
                    v_Ret.setDatas(this.toBeanSerial(v_SolrDocs ,i_BeanCalss));
                }
                else if ( i_BeanCalss == String.class )
                {
                    List<T> v_Datas = new ArrayList<T>();
                    for (SolrDocument v_SDoc : v_SolrDocs)
                    {
                        v_Datas.add((T)v_SDoc.toString());
                    }
                    v_Ret.setDatas(v_Datas);
                }
                else
                {
                    v_Ret.setDatas(this.toBean(v_SolrDocs ,i_BeanCalss));
                }
                
                if ( i_SQuery.isAllowFacet() )
                {
                    v_Ret.setFacetDatas(this.getFacetDatas(v_Response.getFacetFields()));
                }
                
                if ( i_SQuery.isAllowHighlight() )
                {
                    v_Ret.setHighlightDatas(this.getHighlightDatas(v_SolrDocs ,v_Response.getHighlighting()));
                }
            }
        }
        catch (Exception exce)
        {
            exce.printStackTrace();
        }
        
        return v_Ret;
    }
    
    
    
    /**
     * 分解及获取统计数据
     * 
     * @author      ZhengWei(HY)
     * @createDate  2014-12-11
     * @version     v1.0
     *
     * @param i_FacetFields
     * @return
     */
    private TablePartition<String ,Count> getFacetDatas(List<FacetField> i_FacetFields)
    {
        TablePartition<String ,Count> v_Ret = new TablePartition<String ,Count>(i_FacetFields.size());
        
        for (FacetField v_FacetField : i_FacetFields)
        {
            String      v_FieldName = v_FacetField.getName();
            List<Count> v_Counts    = v_FacetField.getValues();
            
            for (Count v_Count : v_Counts)
            {
                // 只有大于0的，才返回
                if ( v_Count.getCount() > 0 )
                {
                    v_Ret.putRow(v_FieldName ,v_Count);
                }
            }
        }
        
        return v_Ret;
    }
    
    
    
    /**
     * 分解及获取高亮数据
     * 
     * @author      ZhengWei(HY)
     * @createDate  2014-12-12
     * @version     v1.0
     *
     * @param i_SolrDocs
     * @param i_Highlights
     * @return
     */
    private List<Object> getHighlightDatas(SolrDocumentList i_SolrDocs ,Map<String, Map<String, List<String>>> i_Highlights)
    {
        List<Object> v_Ret = new ArrayList<Object>(i_Highlights.size());
        
        for (SolrDocument v_Doc : i_SolrDocs) 
        {
            String                    v_ID    = (String)v_Doc.getFieldValue("id");
            Map<String, List<String>> v_HLMap = i_Highlights.get(v_ID);
            
            if ( !Help.isNull(v_HLMap) ) 
            {
                v_Ret.add(v_HLMap);
            }
        }
        
        return v_Ret;
    }
    
    
    
    /**
     * 将Solr结果集转为Java Bean
     * 
     * @param i_SolrDocs
     * @param i_BeanClass
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private List<Map<String ,?>> toBean(SolrDocumentList i_SolrDocs)
    {
        List<Map<String ,?>> v_Beans = new ArrayList<Map<String ,?>>(i_SolrDocs.size());
        
        for (int i=0; i<i_SolrDocs.size(); i++)
        {
            v_Beans.add(i_SolrDocs.get(i).getFieldValuesMap());
        }
        
        return v_Beans;
    }
    
    
    
    /**
     * 将Solr结果集转为Java Bean
     * 
     * @param i_SolrDocs
     * @param i_BeanClass
     * @return
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    private <T> List<T> toBeanSerial(SolrDocumentList i_SolrDocs ,Class<T> i_BeanClass) throws InstantiationException, IllegalAccessException
    {
        List<T> v_Beans = new ArrayList<T>(i_SolrDocs.size());
        
        for (Iterator<SolrDocument> v_Iter = i_SolrDocs.iterator(); v_Iter.hasNext(); ) 
        {
            SolrDocument    v_SolrDocument = v_Iter.next();
            T               v_Bean        = i_BeanClass.newInstance();
            SerializableDef v_BeanSerial  = (SerializableDef)v_Bean;
            
            v_BeanSerial.init(v_SolrDocument.getFieldValueMap());
            v_Beans.add(v_Bean);
        }
        
        return v_Beans;
    }
    
    
    
    /**
     * 将Solr结果集转为Java Bean
     * 
     * @param i_SolrDocs
     * @param i_BeanClass
     * @return
     */
    private <T> List<T> toBean(SolrDocumentList i_SolrDocs ,Class<T> i_BeanClass)
    {
        List<T> v_Beans = null;
        
        // 注意：i_BeanClass 这类的属性要用 @Field 注释，要不无法填充值
        v_Beans = (List<T>) this.getSolr().getBinder().getBeans(i_BeanClass ,i_SolrDocs);
        
        return v_Beans;
    }
    
    

    public int getConnTimeout()
    {
        return connTimeout;
    }


    
    public void setConnTimeout(int connTimeout)
    {
        this.connTimeout = connTimeout;
        
        if ( this.solrServer != null )
        {
            this.solrServer.setConnectionTimeout(this.connTimeout);
        }
    }


    
    public int getMaxConnPerHost()
    {
        return maxConnPerHost;
    }


    
    public void setMaxConnPerHost(int maxConnPerHost)
    {
        this.maxConnPerHost = maxConnPerHost;
        
        if ( this.solrServer != null )
        {
            this.solrServer.setDefaultMaxConnectionsPerHost(this.maxConnPerHost);
        }
    }


    
    public int getMaxConnTotal()
    {
        return maxConnTotal;
    }


    
    public void setMaxConnTotal(int maxConnTotal)
    {
        this.maxConnTotal = maxConnTotal;
        
        if ( this.solrServer != null )
        {
            this.solrServer.setMaxTotalConnections(this.maxConnTotal);
        }
    }

    
    
    public int getMaxRetries()
    {
        return maxRetries;
    }
    

    
    public void setMaxRetries(int maxRetries)
    {
        this.maxRetries = maxRetries;
        
        if ( this.solrServer != null )
        {
            this.solrServer.setMaxRetries(this.maxRetries);
        }
    }



    public String getBaseURL()
    {
        return baseURL;
    }
    
}
