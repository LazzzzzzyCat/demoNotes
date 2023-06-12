package com.demo;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huwj
 * @date 2020-11-12 09:11
 */
public class EsSearchDemo {

    private static final String index = "country";
    private RestHighLevelClient client;

    public void initClient() {
        HttpHost httpHost = new HttpHost("127.0.0.1", 9200);
        RestClientBuilder restClientBuilder = RestClient.builder(httpHost);
        client = new RestHighLevelClient(restClientBuilder);
    }

    @Before
    public void connectTest() {
        initClient();
    }

    @Test
    public void createIndexTest() throws IOException {
        IndexRequest indexRequest = new IndexRequest(index);
        Map<String, Object> settings = new HashMap<>();
        settings.put("number_of_shards", 5);
        settings.put("number_of_replicas", 1);
        indexRequest.source(settings);
        IndexResponse index = client.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(index.getIndex());
    }

    @Test
    public void findByTerm() throws IOException {
        //request
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("book");


        //查询方法以及条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termQuery("name", "西游记"));

        searchRequest.source(builder);

        //查询结果
        SearchResponse resp = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = resp.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit);
        }
    }

    @Test
    public void findByTerms() throws IOException {
        //request
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("book");


        //查询方法以及条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termsQuery("name", "西游记", "三国演义"));

        searchRequest.source(builder);

        //查询结果
        SearchResponse resp = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = resp.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.print(hit);
        }
    }

    @Test
    public void findByMatchAll() throws IOException {
        //request
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("book");


        //查询方法以及条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());

        searchRequest.source(builder);

        //查询结果
        SearchResponse resp = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = resp.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit);
        }
    }

    @Test
    public void findByMatch() throws IOException {
        //request
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("book");


        //查询方法以及条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchQuery("name", "西游"));

        searchRequest.source(builder);

        //查询结果
        SearchResponse resp = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = resp.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit);
        }
    }

    @Test
    public void findByMatchBoolean() throws IOException {
        //request
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("book");


        //查询方法以及条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchBoolPrefixQuery("description", "之一"));

        searchRequest.source(builder);

        //查询结果
        SearchResponse resp = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = resp.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit);
        }
    }
}
