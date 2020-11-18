package com.demo;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * 查询接口测试
 * @author huwj
 * @date 2020-11-18 09:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsSearchApiTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private static final String INDEX_DRUG = "drug";

    @Test
    public void termQueryTest() {
        this.doQuery(QueryBuilders.termQuery("artno", "0000001"));
    }

    @Test
    public void termsQueryTest(){
        this.doQuery(QueryBuilders.termsQuery("artno", "0000001", "201907310049"));
    }

    @Test
    public void matchQueryTest(){
        this.doQuery(QueryBuilders.matchQuery("manuftidname", "福建"));
    }

    @Test
    public void multiMatchQueryTest(){
        this.doQuery(QueryBuilders.multiMatchQuery("福建", "manuftidname", "drugname"));
    }

    @Test
    public void fuzzyQueryTest(){
        this.doQuery(QueryBuilders.fuzzyQuery("manuftidname", "瑞典"));
    }

    @Test
    public void prefixQueryTest(){
        this.doQuery(QueryBuilders.prefixQuery("manuftidname", "瑞典"));
    }

    @Test
    public void boostingQueryTest(){

    }

    @Test
    public void boolQueryTest(){

    }

    private void doQuery(QueryBuilder queryBuilder) {
        SearchRequest searchRequest = new SearchRequest(INDEX_DRUG);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response;
        try {
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("检索失败");
        }
        SearchHit[] hits = response.getHits().getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsMap());
        }
    }

}
