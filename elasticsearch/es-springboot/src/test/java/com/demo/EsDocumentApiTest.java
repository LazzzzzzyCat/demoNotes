package com.demo;

import com.demo.drug.domain.DrugPo;
import com.demo.drug.maper.DrugMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 文档接口测试
 * @author huwj
 * @date 2020-11-18 09:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsDocumentApiTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Resource
    private DrugMapper drugMapper;

    private static final String INDEX_DRUG = "drug";

    @Test
    public void createDocumentTest() throws IOException {
        IndexRequest indexRequest = new IndexRequest(INDEX_DRUG);
        String drugid = "2f7276768f7341b2b2c71b37272b5749";
        DrugPo item = drugMapper.queryById(drugid);
        indexRequest.id(item.getDrugid());

        ObjectMapper objectMapper = new ObjectMapper();
        indexRequest.source(objectMapper.writeValueAsString(item), XContentType.JSON);
        IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(index.getIndex());
    }

    @Test
    public void bulkTest() throws IOException {
        BulkRequest bulkRequest = new BulkRequest(INDEX_DRUG);

        List<DrugPo> drugs = drugMapper.queryAll();
        ObjectMapper objectMapper = new ObjectMapper();
        for (DrugPo item:drugs) {
            IndexRequest indexRequest = new IndexRequest();
            indexRequest.id(item.getDrugid());
            indexRequest.source(objectMapper.writeValueAsString(item), XContentType.JSON);
            bulkRequest.add(indexRequest);
        }

        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk.hasFailures());
    }

    @Test
    public void deleteByQueryTest() throws IOException {
        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest(INDEX_DRUG);

        deleteByQueryRequest.setQuery(QueryBuilders.matchAllQuery());

        BulkByScrollResponse bulkByScrollResponse = restHighLevelClient.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);
        System.out.println(bulkByScrollResponse.getBulkFailures());
    }

}
