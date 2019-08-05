package utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

/**
 * $功能描述： ConnectionInstace
 *
 * @author ：smart-dxw
 * @version ： 2019/7/28 22:19 v1.0
 */
public class ConnectionInstance {

    private static Connection conn;

    public static synchronized Connection getConn(Configuration conf) {
        try {
            if (conn == null || conn.isClosed()) {
                conn = ConnectionFactory.createConnection(conf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
