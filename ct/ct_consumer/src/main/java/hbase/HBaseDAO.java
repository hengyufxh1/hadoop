package hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import utils.ConnectionInstance;
import utils.HBaseUtils;
import utils.PropertiesUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * $功能描述： HBaseDAO
 *
 * @author ：smart-dxw
 * @version ： 2019/7/28 22:05 v1.0
 */
public class HBaseDAO {


    private int region;
    private String nameSpace;
    private String tableName;
    private static final Configuration conf;
    private HTable table;
    private Connection conn;
    private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");


    // 用来优化插入数据的，每30条插入一次
    private List<Put> cacheList = new ArrayList<>();

    static {
        conf = HBaseConfiguration.create();
    }

    public HBaseDAO() {
        try {
            nameSpace = PropertiesUtils.getProperty("hbase.calllog.namespace");
            tableName = PropertiesUtils.getProperty("hbase.calllog.tableName");
            region = Integer.valueOf(PropertiesUtils.getProperty("hbase.calllog.regions"));

            if (!HBaseUtils.isTable(conf, tableName)) {
                HBaseUtils.initNameSpace(conf, nameSpace);
//                f1 列簇存放主叫数据，f2存放被叫数据
                HBaseUtils.createTable(conf, tableName, region, "f1", "f2");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据样式：19902496992,17269452013,2019-04-26 14:29:12,1017
     *
     * @param value 19902496992,17269452013,2019-04-26 14:29:12,1017
     */
    public void put(String value) {
        try {
            // 数据优化，节省10，每30条放一次
            if (cacheList.size() == 0) {
                conn = ConnectionInstance.getConn(conf);
                table = (HTable) conn.getTable(TableName.valueOf(tableName));
                table.setAutoFlushTo(false);
                table.setWriteBufferSize(2 * 1024 * 1024);
            }


            String[] splitOri = value.split(",");
            String caller = splitOri[0];
            String callee = splitOri[1];
            String buildTime = splitOri[2];
            String duration = splitOri[3];

            String regionCode = HBaseUtils.genRegionCode(caller, buildTime, region);


            String buildTimeReplace = sdf2.format(sdf1.parse(buildTime));
            String buildTiemTS = String.valueOf(sdf1.parse(buildTime).getTime());


            String rowKey = HBaseUtils.genRowKey(regionCode,caller,buildTimeReplace,callee,"1",duration);

            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes("f1"),Bytes.toBytes("caller"),Bytes.toBytes(caller));
            put.addColumn(Bytes.toBytes("f1"),Bytes.toBytes("callee"),Bytes.toBytes(callee));
            put.addColumn(Bytes.toBytes("f1"),Bytes.toBytes("buildTime"),Bytes.toBytes(buildTime));
            put.addColumn(Bytes.toBytes("f1"),Bytes.toBytes("buildTime_TS"),Bytes.toBytes(buildTiemTS));
            put.addColumn(Bytes.toBytes("f1"),Bytes.toBytes("flag"),Bytes.toBytes(1));
            put.addColumn(Bytes.toBytes("f1"),Bytes.toBytes("duration"),Bytes.toBytes(duration));

            cacheList.add(put);

            if (cacheList.size() >=30){
                table.put(cacheList);
                table.flushCommits();
                table.close();
                cacheList.clear();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
