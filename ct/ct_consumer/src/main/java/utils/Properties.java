package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * $功能描述： Properties
 *
 * @author ：smart-dxw
 * @version ： 2019/7/28 20:40 v1.0
 */
public class PropertiesUtils {

    public static Properties properties = null;

    static {

        InputStream is = ClassLoader.getSystemResourceAsStream("hbase_consumer.properties");

        properties = new Properties();

        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String name){
        return properties.getProperty(name);
    }
}
