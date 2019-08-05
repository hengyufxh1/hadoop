package kafka;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import utils.PropertiesUtils;

import java.util.Arrays;

/**
 * $功能描述： HBaseConsumer
 *
 * @author ：smart-dxw
 * @version ： 2019/7/28 20:47 v1.0
 */
public class HBaseConsumer {

    public static void main(String[] args) {
        // kafka消费者的新API
        // 消费和 API
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(PropertiesUtils.properties);
        // 获取kafka Topic 的主题
        kafkaConsumer.subscribe(Arrays.asList(PropertiesUtils.getProperty("kafka.topics")));

        // 创建写入HBase的对象
//        HBaseDAO baseDao = new HBaseDAO();
        // 遍历打印数据
        while (true) {
            // 获得主题拉取消费者数据
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            // 遍历打印数据
            for (ConsumerRecord<String, String> cr : records) {
                String value = cr.value();
                // 15133295266,17868457605,2018-10-08 18:21:21,1672
                System.out.println(value);
                // 把数据灌入到HBase中
//                baseDao.put(value);
            }
        }
    }


}
