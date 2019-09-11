package com.elasticsearch.day0902;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * $功能描述： ESTest_0902
 *
 * @author ：smart-dxw
 * @version ： 2019/9/2 22:18 v1.0
 */
public class ESTest_0902 {

    // 对es所有操作都是通过client
    private TransportClient client;

    @Before
    public void getClient() throws Exception {
        // 1设置连接的集群名称
        Settings settings = Settings.builder().put("cluster.name", "my-application").build();

        // 2连接集群
        client = new PreBuiltTransportClient(settings);

        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("11.11.11.151"), 9300));

    }

    /**
     * 创建索引
     */
    @Test
    public void createIndex_blog() {
        // 1 创建索引
        client.admin().indices().prepareCreate("kibana_data").get();
        // 2 关闭连接
        client.close();
    }

    /**
     * 删除索引
     */
    @Test
    public void deleteIndex() {
        client.admin().indices().prepareDelete("blog1").get();
        client.close();
    }


    /**
     * json
     * 添加一个
     */
    @Test
    public void createIndexDataByJson() {
        for(int i = 100;i<=100;i++){
            System.out.println(i+"在添加");
            // 使用json 创建document
            // 1 文档的数据准备
            String json = "{" + "\"id\":\""+i+"\"," + "\"title\":\""+i+"、基于Lucene的搜索服务器\","
                    + "\"content\":\"它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口\"" + "}";
            // 2 创建文档
            IndexResponse indexResponse = client.prepareIndex("kibana_data", "article", i+"").setSource(json).execute().actionGet();

//            isClose(indexResponse);
        }
    }


    /**
     * Map
     */
    @Test
    public void ceateByMap() {
        Map<String, Object> json = new HashMap<String, Object>();

        json.put("id", 222);
        json.put("title", "基于Lucene的搜索服务器");
        json.put("content", "它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口");

        IndexResponse indexResponse = client.prepareIndex("blog1", "article", "2").setSource(json).execute().actionGet();

        isClose(indexResponse);
    }

    /**
     * XContentBuilder
     *
     * @throws Exception
     */
    @Test
    public void createIndexDataByXContent() throws Exception {
        // es建议的拼接的方式

        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .field("id", 333)
                .field("title", "基于Lucene的搜索服务器")
                .field("content", "它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口")
                .endObject();

        IndexResponse indexResponse = client.prepareIndex("blog1", "article", "3").setSource(builder).get();
        isClose(indexResponse);

    }

    public void isClose(IndexResponse indexResponse) {
        // 3 打印出返回的结果
        System.out.println("index:" + indexResponse.getIndex());
        System.out.println("type:" + indexResponse.getType());
        System.out.println("id:" + indexResponse.getId());
        System.out.println("version:" + indexResponse.getVersion());
        System.out.println("result:" + indexResponse.getResult());

        // 4 关闭连接
        client.close();
    }

    @Test
    public void getG() {
        GetResponse response = client.prepareGet("blog1", "article", "2").get();
        System.out.println(response.getSourceAsString());
        client.close();
    }


    @Test
    public void getMultiData() {
        // 查询多个文档
        MultiGetResponse response = client.prepareMultiGet()
                .add("blog4", "article", "1")
                .add("blog4", "article", "2")
                .add("blog4", "article", "3")
                .add("blog4", "article", "4")
                .add("blog4", "article", "1", "2", "3").get();

        // 2 遍历返回的结果
        for (MultiGetItemResponse itemResponse : response) {
            GetResponse getResponse = itemResponse.getResponse();
            // 如果获取到查询结果
            if (getResponse.isExists()) {
                String sourceAsString = getResponse.getSourceAsString();
                System.out.println(sourceAsString);
            }
        }
        // 3 关闭资源
        client.close();
    }

    @Test
    public void updateDate() throws Exception {
        // 创建更新数据 的请求对象
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("blog1");
        updateRequest.type("article");
        updateRequest.id("3");

        updateRequest.doc(XContentFactory.jsonBuilder()
                .startObject()
                .field("id", 333)
                .field("title", "基于Lucene的搜索服务器")
                .field("content", "它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口")
                .endObject());

        UpdateResponse indexResponse = client.update(updateRequest).get();

        // 4 关闭连接
        client.close();
    }


    @Test
    public void testUpsert() throws Exception {
        // 设置查询条件, 查找不到则添加
        IndexRequest indexRequest = new IndexRequest("blog1", "article", "5")
                .source(XContentFactory.jsonBuilder().startObject().field("title", "搜索服务器").field("content", "它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口。Elasticsearch是用Java开发的，并作为Apache许可条款下的开放源码发布，是当前流行的企业级搜索引擎。设计用于云计算中，能够达到实时搜索，稳定，可靠，快速，安装使用方便。").endObject());

        // 设置更新, 查找到更新下面的设置
        UpdateRequest upsert = new UpdateRequest("blog1", "article", "5")
                .doc(XContentFactory.jsonBuilder().startObject().field("user", "李四").endObject()).upsert(indexRequest);

        client.update(upsert).get();
        client.close();
    }

    @Test
    public void deleteData() {
        DeleteResponse indexResponse = client.prepareDelete("blog1", "article", "1").get();
        // 2 打印返回的结果
        System.out.println("index:" + indexResponse.getIndex());
        System.out.println("type:" + indexResponse.getType());
        System.out.println("id:" + indexResponse.getId());
        System.out.println("version:" + indexResponse.getVersion());
        System.out.println("found:" + indexResponse.getResult());
        client.close();
    }

    @Test
    public void matchAllQuery() {
        // 相当于 查询所有select * from emp
        SearchResponse searchResponse = client.prepareSearch("blog1")
                .setTypes("artlcle")
                .setQuery(QueryBuilders.matchAllQuery()).get();

        SearchHits hits = searchResponse.getHits();

        System.out.println("查询结果有：" + hits.getTotalHits());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());//打印出每条结果
        }

        // 3 关闭连接
        client.close();
    }

    @Test
    public void query() {

        // 1 通配符查询
        SearchResponse searchResponse = client.prepareSearch("blog1").setTypes("article")
                .setQuery(QueryBuilders.queryStringQuery("web")).get();

        // 2 打印查询结果
        SearchHits hits = searchResponse.getHits(); // 获取命中次数，查询结果有多少对象
        System.out.println("查询结果有：" + hits.getTotalHits() + "条");

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());//打印出每条结果
        }

        // 3 关闭连接
        client.close();
    }


    /**
     * 通配符
     */
    @Test
    public void wildcardQuery() {
        // 1 条件查询
        SearchResponse searchResponse = client.prepareSearch("blog1").setTypes("article")
                .setQuery(QueryBuilders.wildcardQuery("content", "*全*")).get();

        // 2 打印查询结果
        SearchHits hits = searchResponse.getHits(); // 获取命中次数，查询结果有多少对象
        System.out.println("查询结果有：" + hits.getTotalHits() + "条");

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());//打印出每条结果
        }

        // 3 关闭连接
        client.close();
    }


    /**
     * 全文
     */
    @Test
    public void termQuery() {
        // 1 条件查询
        // 类似于 mysql 中的 =
        /**
         * = 不是与字段等于
         * 是与字段的分词结果等于
         */
        SearchResponse searchResponse = client.prepareSearch("blog1").setTypes("article")
                .setQuery(QueryBuilders.wildcardQuery("content", "RESTful")).get();

        // 2 打印查询结果
        SearchHits hits = searchResponse.getHits(); // 获取命中次数，查询结果有多少对象
        System.out.println("查询结果有：" + hits.getTotalHits() + "条");

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());//打印出每条结果
        }

        // 3 关闭连接
        client.close();
    }

    @Test
    public void fuzzy() {

        // 1 模糊查询
        SearchResponse searchResponse = client.prepareSearch("blog1").setTypes("article")
                .setQuery(QueryBuilders.fuzzyQuery("content", "基于RESTful")).get();

        // 2 打印查询结果
        SearchHits hits = searchResponse.getHits(); // 获取命中次数，查询结果有多少对象
        System.out.println("查询结果有：" + hits.getTotalHits() + "条");

        Iterator<SearchHit> iterator = hits.iterator();

        while (iterator.hasNext()) {
            SearchHit searchHit = iterator.next(); // 每个查询对象

            System.out.println(searchHit.getSourceAsString()); // 获取字符串格式打印
        }

        // 3 关闭连接
        client.close();
    }


    /**
     * 创建mapping
     * @throws Exception
     */
    @Test
    public void createMapping() throws Exception {

        // 1设置mapping
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("article")
                .startObject("properties")
                .startObject("id")
                .field("type", "text")
                .field("store", "false")
                .endObject()
                .startObject("title")
                .field("type", "text")
                .field("store", "false")
                .endObject()
                .startObject("content")
                .field("type", "text")
                .field("store", "true")
                .endObject()
                .endObject()
                .endObject()
                .endObject();

        // 2 添加mapping
        PutMappingRequest mapping = Requests.putMappingRequest("blog1").type("article").source(builder);

        client.admin().indices().putMapping(mapping).get();

        // 3 关闭资源
        client.close();
    }

}

