//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.hbase.*;
//import org.apache.hadoop.hbase.client.*;
//import org.apache.hadoop.hbase.util.Bytes;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * $功能描述： HbaseAPI
// *
// * @author ：smart-dxw
// * @version ： 2019/7/20 0:27 v1.0
// */
//public class HbaseAPI {
//
//    public static Configuration conf;
//
//    static {
//        //使用HBaseConfiguration的单例方法实例化
//        conf = HBaseConfiguration.create();
//        conf.set("hbase.zookeeper.quorum", "asd1");
//        conf.set("hbase.zookeeper.property.clientPort", "2181");
//        conf.set("zookeeper.znode.parent", "/hbase");
//    }
//
//
//    /**
//     * 判断表是否存在
//     * @param tableName
//     * @return
//     * @throws MasterNotRunningException
//     * @throws ZooKeeperConnectionException
//     * @throws IOException
//     */
//    @Test
//    public static boolean isTableExist(String tableName) throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
//        //在HBase中管理、访问表需要先创建HBaseAdmin对象
//        Connection connection = ConnectionFactory.createConnection(conf);
////        HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
//        HBaseAdmin admin = new HBaseAdmin(conf);
//        return admin.tableExists("student");
//    }
//
//    /**
//     * 创建表
//     * @param tableName
//     * @param columnFamily
//     * @throws MasterNotRunningException
//     * @throws ZooKeeperConnectionException
//     * @throws IOException
//     */
//    @Test
//    public static void createTable(String tableName, String... columnFamily) throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
//        tableName = "student1";
//        columnFamily[0] = "'1001'";
//        columnFamily[1] = "info.name";
//        columnFamily[2] = "Thomas'";
//        HBaseAdmin admin = new HBaseAdmin(conf);
//        //判断表是否存在
//        if (isTableExist(tableName)) {
//            System.out.println("表" + tableName + "已存在");
//            //System.exit(0);
//        } else {
//            //创建表属性对象,表名需要转字节
//            HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(tableName));
//            //创建多个列族
//            for (String cf : columnFamily) {
//                descriptor.addFamily(new HColumnDescriptor(cf));
//            }
//            //根据对表的配置，创建表
//            admin.createTable(descriptor);
//            System.out.println("表" + tableName + "创建成功！");
//        }
//    }
//
//    /**
//     * 删除表
//     * @param tableName
//     * @throws Exception
//     */
//    public static void dropTable(String tableName) throws Exception {
//        HBaseAdmin admin = new HBaseAdmin(conf);
//        if (isTableExist(tableName)) {
//            admin.disableTable(tableName);
//            admin.deleteTable(tableName);
//            System.out.println("表" + tableName + "删除成功！");
//        } else {
//            System.out.println("表" + tableName + "不存在！");
//        }
//    }
//
//    /**
//     * 向表中插入数据
//     * @param tableName
//     * @param rowKey
//     * @param columnFamily
//     * @param column
//     * @param value
//     * @throws Exception
//     */
//    public static void addRowData(String tableName, String rowKey, String columnFamily, String column, String value) throws Exception {
//        //创建HTable对象
//        HTable hTable = new HTable(conf, tableName);
//        //向表中插入数据
//        Put put = new Put(Bytes.toBytes(rowKey));
//        //向Put对象中组装数据
//        put.add(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
//        hTable.put(put);
//        hTable.close();
//        System.out.println("插入数据成功");
//    }
//
//    /**
//     * 删除多行数据
//     * @param tableName
//     * @param rows
//     * @throws IOException
//     */
//    public static void deleteMultiRow(String tableName, String... rows) throws IOException {
//        HTable hTable = new HTable(conf, tableName);
//        List<Delete> deleteList = new ArrayList<Delete>();
//        for (String row : rows) {
//            Delete delete = new Delete(Bytes.toBytes(row));
//            deleteList.add(delete);
//        }
//        hTable.delete(deleteList);
//        hTable.close();
//    }
//
//    /**
//     * 得到所有数据
//     * @param tableName
//     * @throws IOException
//     */
//    public static void getAllRows(String tableName) throws IOException {
//        HTable hTable = new HTable(conf, tableName);
//        //得到用于扫描region的对象
//        Scan scan = new Scan();
//        //使用HTable得到resultcanner实现类的对象
//        ResultScanner resultScanner = hTable.getScanner(scan);
//        for (Result result : resultScanner) {
//            Cell[] cells = result.rawCells();
//            for (Cell cell : cells) {
//                //得到rowkey
//                System.out.println("行键:" + Bytes.toString(CellUtil.cloneRow(cell)));
//                //得到列族
//                System.out.println("列族" + Bytes.toString(CellUtil.cloneFamily(cell)));
//                System.out.println("列:" + Bytes.toString(CellUtil.cloneQualifier(cell)));
//                System.out.println("值:" + Bytes.toString(CellUtil.cloneValue(cell)));
//            }
//        }
//    }
//
//    /**
//     * 得到某一行所有数据
//     * @param tableName
//     * @param rowKey
//     * @throws IOException
//     */
//    public static void getRow(String tableName, String rowKey) throws IOException {
//        HTable table = new HTable(conf, tableName);
//        Get get = new Get(Bytes.toBytes(rowKey));
//        //get.setMaxVersions();显示所有版本
////get.setTimeStamp();显示指定时间戳的版本
//        Result result = table.get(get);
//        for (Cell cell : result.rawCells()) {
//            System.out.println("行键:" + Bytes.toString(result.getRow()));
//            System.out.println("列族" + Bytes.toString(CellUtil.cloneFamily(cell)));
//            System.out.println("列:" + Bytes.toString(CellUtil.cloneQualifier(cell)));
//            System.out.println("值:" + Bytes.toString(CellUtil.cloneValue(cell)));
//            System.out.println("时间戳:" + cell.getTimestamp());
//        }
//    }
//
//    /**
//     * 获取某一行指定“列族:列”的数据
//     * @param tableName
//     * @param rowKey
//     * @param family
//     * @param qualifier
//     * @throws IOException
//     */
//    public static void getRowQualifier(String tableName, String rowKey, String family, String qualifier) throws IOException {
//        HTable table = new HTable(conf, tableName);
//        Get get = new Get(Bytes.toBytes(rowKey));
//        get.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
//        Result result = table.get(get);
//        for (Cell cell : result.rawCells()) {
//            System.out.println("行键:" + Bytes.toString(result.getRow()));
//            System.out.println("列族" + Bytes.toString(CellUtil.cloneFamily(cell)));
//            System.out.println("列:" + Bytes.toString(CellUtil.cloneQualifier(cell)));
//            System.out.println("值:" + Bytes.toString(CellUtil.cloneValue(cell)));
//        }
//    }
//}
