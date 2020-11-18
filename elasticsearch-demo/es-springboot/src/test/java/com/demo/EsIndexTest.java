package com.demo;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * 索引接口测试
 * @author huwj
 * @date 2020-11-18 09:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsIndexTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    private static final String INDEX_DRUG = "drug";

    @Test
    public void createIndexTest() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(INDEX_DRUG);
        createIndexRequest.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject()
                    .startObject("properties")
                        .startObject("drugid").field("type", "text").endObject()
                        .startObject("artno").field("type", "keyword").endObject()
                        .startObject("drugname").field("type", "text").field("analyzer", "ik_max_word").endObject()
                        .startObject("barcode").field("type", "text").field("analyzer", "ik_max_word").endObject()
                        .startObject("gencpycode").field("type", "text").field("analyzer", "ik_max_word").endObject()
                        .startObject("manuftidname").field("type", "text").endObject()
                        .startObject("taxrto").field("type", "half_float").endObject()
                        .startObject("store_ip").field("type", "ip").endObject()
                        .startObject("last_update").field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd").endObject()
                    .endObject()
                .endObject();

        createIndexRequest.mapping(builder);
        CreateIndexResponse response = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(response.index());
    }

    @Test
    public void indexExistTest() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest(INDEX_DRUG);
        boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    @Test
    public void deleteIndexTest() throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(INDEX_DRUG);
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());
    }

}
