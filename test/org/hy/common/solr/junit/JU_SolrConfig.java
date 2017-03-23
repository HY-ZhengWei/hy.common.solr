package org.hy.common.solr.junit;

import java.util.Map;

import org.hy.common.Date;
import org.hy.common.Help;
import org.hy.common.solr.SPage;
import org.hy.common.solr.SQuery;
import org.hy.common.solr.SResult;
import org.hy.common.solr.Solr;
import org.hy.common.solr.field.SFieldQuery;
import org.hy.common.xml.XJava;
import org.hy.common.xml.annotation.Xjava;
import org.hy.common.xml.annotation.XType;





/**
 * 测试单元：
 * 
 * @author      ZhengWei(HY)
 * @version     v1.0  
 * @createDate  2014-12-11
 */
@Xjava(XType.XML)
public class JU_SolrConfig
{
    
    public static void main(String [] args) throws Exception
    {
        org.hy.common.xml.XJava.parserAnnotation("org.hy.common.solr.junit");
        
        
        // 克隆测试-01
        SQuery      v_SQuery = getSQuery("SQ_Item");
        SFieldQuery v_SField = v_SQuery.getQueryWord();
        
        v_SField.setFieldName("新的FieldName");
        v_SField.setFieldValue("新的FieldValue");
        
        v_SQuery.setQ("p:HY");
        v_SQuery.toString();
        v_SQuery.setQueryWord(v_SField);
        
        v_SQuery.getPage().setPerPageSize(999);
        
        
        System.out.println(v_SQuery);
        System.out.println(getSQuery("SQ_Item"));
        System.out.println(v_SQuery.getPage());
        System.out.println(getSPage());
        
        
        
        System.out.println("\n-- 有统计功能的查询：");
        SResult<Map<String ,?>> v_SResult = getSolr().query(getSQuery("SQ_Item"));
        
        System.out.println("\n-- 总记录数：" + v_SResult.getNumFound());
        System.out.println("\n-- 查询用时时长(毫秒)：" + v_SResult.getQtime());
        
        System.out.println("\n-- 统计信息：");
        Help.print(    v_SResult.getFacetDatas());
        
        System.out.println("\n-- 高亮信息：");
        Help.print(    v_SResult.getHighlightDatas());
        
        System.out.println("\n-- 查询结果：");
        Help.print(    v_SResult.getDatas());
        
        System.out.println("\n-- 查询结果(字符串类型的)：");
        SResult<String> v_SR = getSolr().query(getSQuery("SQ_Item") ,String.class);
        Help.print(    v_SR.getDatas());
        
        
        ItemInfo v_New = new ItemInfo();
        v_New.setId(Date.getNowTime().getFullMilli_ID());
        v_New.setTitle("HY");
        v_New.setCommoNo("HY" + v_New.getId());
        
        getSolr().add(v_New);
    }
    
    
    
    public static Solr getSolr()
    {
        return (Solr)XJava.getObject("SolrServer");
    }
    
    
    
    public static SQuery getSQuery(String i_XID)
    {
        return (SQuery)XJava.getObject("SQ_Item");
    }
    
    
    
    public static SPage getSPage()
    {
        return (SPage)XJava.getObject("SolrPage");
    }
    
}
