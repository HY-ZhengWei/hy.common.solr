package org.hy.common.solr.junit;

import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import org.hy.common.Help;
import org.hy.common.solr.SPage;
import org.hy.common.solr.SQuery;
import org.hy.common.solr.SResult;
import org.hy.common.solr.Solr;





/**
 * Solr查询测试
 * 
 * @author      ZhengWei(HY)
 * @version     v1.0  
 * @createDate  2014-11-03
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class JU_Solr_Query
{
    private Solr  solr;
    
    private SPage page;
    
    
    
    public JU_Solr_Query()
    {
        this.solr = new Solr("http://127.0.0.1:8983/solr472");
        this.page = new SPage(100);
    }
    
    
    
    @Test
    public void test_001_Query()
    {
        // JavaHelp.print(this.solr.query());
        
        // JavaHelp.print(this.solr.query("prod_no" ,"0100000011301"));
        
        System.out.println("\n-- 查询总记录数：");
        System.out.println(this.solr.query(new SQuery((SPage)null)).getNumFound());
        
        System.out.println("\n-- 查询所有数据 10：");
        Help.print(this.solr.query().getDatas());
        
        System.out.println("\n-- 查询所有数据 0 ~ 100：");
        Help.print(this.solr.query(new SQuery(this.page)).getDatas());
        
        System.out.println("\n-- 查询部分数据 0 ~ 100：");
        Help.print(this.solr.query(new SQuery("大金" ,"id" ,this.page) ,ItemInfo.class).getDatas());
        
        System.out.println("\n-- 查询部分数据 10 ~ 20：");
        this.page.setPageNo(2);
        this.page.setPerPageSize(10);
        Help.print(this.solr.query(new SQuery("大金" ,"id" ,this.page) ,ItemInfo.class).getDatas());
    }
    
    
    
    @Test
    public void test_002_Facet()
    {
        SQuery v_SQuery = new SQuery();
        
        v_SQuery.addFilterQuery("{!tag=brand}title" ,"百度");
        v_SQuery.setFacetField("{!ex=brand}brand");
        v_SQuery.setSelectField("title");
        
        System.out.println("\n-- 有统计功能的查询：");
        SResult<Map<String ,?>> v_SResult = this.solr.query(v_SQuery);
        System.out.println("\n-- 总记录数：" + v_SResult.getNumFound());
        Help.print(    v_SResult.getDatas());
        System.out.println("\n-- 统计信息：");
        Help.print(    v_SResult.getFacetDatas());
    }
    
}
