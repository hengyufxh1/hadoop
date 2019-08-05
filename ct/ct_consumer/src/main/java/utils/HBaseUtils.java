package utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * $功能描述： HBaseUtils
 * 1.命名空间（数据库：database）
 * 2.表（数据表：table）
 * 判断表 istable
 * 创建表 createTable
 * rowKey regionCode_caller_buildTime_callee_flag_duration
 * region == 6
 * 随机
 * 列簇
 * 列
 * 协处理器    observer ***
 *
 * @author ：smart-dxw
 * @version ： 2019/7/28 21:01 v1.0
 */
public class HBaseUtils {

    /**
     * 初始化命名空间
     *
     * @param conf      配置
     * @param nameSpace 空间的名字
     */
    public static void initNameSpace(Configuration conf, String nameSpace) throws IOException {
        Connection connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();

        // 命名空间描述器
        NamespaceDescriptor build = NamespaceDescriptor
                .create(nameSpace)
                .addConfiguration("CREATE_TIEM", String.valueOf(System.currentTimeMillis()))
                .build();

        admin.createNamespace(build);
        admin.close();
        connection.close();
    }

    /**
     * 判断表是否存在
     *
     * @param conf
     * @param tableName
     * @return
     */
    public static boolean isTable(Configuration conf, String tableName) throws IOException {
        // 创建connection对象
        Connection connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();
        // 判断表 API
        boolean b = admin.tableExists(TableName.valueOf(tableName));

        // 关闭
        close(admin, connection);
        return b;

    }

    /**
     * 创建表
     *
     * @param conf         配置
     * @param tableName    表面字
     * @param regions      多个rw
     * @param columnFamily 列簇
     */
    public static void createTable(Configuration conf, String tableName, int regions, String... columnFamily) throws IOException {
        // 创建connection对象
        Connection connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();

        if (isTable(conf, tableName)) return;

        HTableDescriptor htd = new HTableDescriptor(TableName.valueOf(tableName));
        for (String cf : columnFamily) {
            htd.addFamily(new HColumnDescriptor(cf));
        }

        admin.createTable(htd, genSplitKey(regions));
        admin.close();
        connection.close();

    }

    /**
     * 均匀分regions
     *
     * @param regions 个数
     * @return 二维数组
     */
    private static byte[][] genSplitKey(int regions) {
        String[] keys = new String[regions];

        DecimalFormat df = new DecimalFormat("00");
        for (int i = 0; i < regions; i++) {
            // 为什么是"|"，| ASCII它是最大的
            keys[i] = df.format(i) + "|";
        }

        byte[][] splitsKey = new byte[regions][];
        TreeSet<byte[]> treeSet = new TreeSet<>(Bytes.BYTES_COMPARATOR);
        for (int i = 0; i < regions; i++) {
            treeSet.add(Bytes.toBytes(keys[i]));
        }

        Iterator<byte[]> splitKeysIteratot = treeSet.iterator();
        int index = 0;
        while (splitKeysIteratot.hasNext()) {
            byte[] next = splitKeysIteratot.next();
            splitsKey[index++] = next;
        }
        return splitsKey;
    }


    /**
     * RW构成 regionCode_caller_buildTime_callee_flag_duration
     *
     * @param regionCode 分区号
     * @param caller     主叫
     * @param builTime   通话建立时间
     * @param callee     被叫
     * @param flag       主被叫标记
     * @param duration   持续时间
     * @return
     */
    public static String genRowKey(String regionCode, String caller, String builTime, String callee, String flag, String duration) {
        StringBuilder sb = new StringBuilder();
        sb.append(regionCode + "_")
                .append(caller + "_")
                .append(builTime + "_")
                .append(callee + "_")
                .append(flag + "_")
                .append(duration);
        return sb.toString();
    }


    public static String genRegionCode(String caller, String buildTime, int regions) {
        int length = caller.length();
        // 电话后四位
        String lastPhone = caller.substring(length - 4);
        String ym = buildTime.replaceAll("-", "").substring(0, 6);
        Integer x = Integer.valueOf(lastPhone) ^ Integer.valueOf(ym);
        Integer y = x.hashCode();
        Integer regionCode = y % regions;
        DecimalFormat df = new DecimalFormat("00");
        return df.format(regionCode);
    }


    /**
     * 关闭
     *
     * @param admin
     * @param connection
     * @return
     */
    public static void close(Admin admin, Connection connection) throws IOException {
        if (admin != null) {
            admin.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}
