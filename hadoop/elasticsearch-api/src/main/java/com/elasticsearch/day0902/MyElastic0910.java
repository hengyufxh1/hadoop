package com.elasticsearch.day0902;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * $功能描述： MyElastic
 * kibana_data 添加随机数据进行测试
 * @author ：smart-dxw
 * @version ： 2019/7/15 1:47 v1.0
 */
public class MyElastic0910 {

    private TransportClient client;

    @Before
    public void getClient() throws Exception {
        // 1设置连接的集群名称
        Settings settings = Settings.builder().put("cluster.name", "my-application").build();

        // 2连接集群
        client = new PreBuiltTransportClient(settings);

        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("11.11.11.151"), 9300));
    }

    @Test
    public void kibana_data_qenerate() {
        // 创建索引
        client.admin().indices().prepareCreate("kibana_data").get();

        // 创建示例数据


        // 使用map创建document
        // 文档数据准备
        Map<String, Object> json = null;
        IndexResponse indexResponse = null;
        for (int i = 1; i <= 5000; i++) {
            json = new HashMap<String, Object>();
            json.put("id", i + "");
            json.put("status_code", generateStatusCode() + "");
            json.put("response_fitiem", Math.random());
            indexResponse = client.prepareIndex("kibana_data", "access_log", i+"").setSource(json).execute().actionGet();
        }

        // 关闭连接
        client.close();
    }

    private int generateStatusCode() {
        int status_code = 0;
        double randomNum = Math.random();
        if (randomNum <= 0.5) {
            status_code = 200;
        } else if (randomNum <= 0.6) {
            status_code = 400;
        } else if (randomNum <= 0.7) {
            status_code = 404;
        } else if (randomNum <= 0.8) {
            status_code = 500;
        } else {
            status_code = 401;
        }
        return status_code;
    }
}
