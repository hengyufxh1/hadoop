package com.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * $功能描述： HbaseAPI_01
 *
 * @author ：smart-dxw
 * @version ： 2019/7/22 20:10 v1.0
 */
public class HbaseAPI_01 {

    public static Configuration conf;

    public static String tablename = "student3";

    public static void main(String[] args) {
        // 判断是否存在
//        System.out.println(isTableExist(tablename));
        // 创建表
//        createTable(tablename, "cf1", "cf2");

        // 插入数据
//        putTableData(tablename,"1005","cf1","name","zhangsa5");

        // 扫描表
//        scanTable(tablename);


        // 条件删除行
//        deleteMultiRow("student3","1005","1004");

        // 查询某一条
        getRowQualifier(tablename, "1004", "cf1", "name");

        // 删除表
//        dropTable(tablename);
    }


    /**
     * hbase 的初始化
     */

    static {
        // 使用HBaseConfiguration的单利方法实例化
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "asd1");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("zookeeper.znode.parent", "/hbase");
    }


    /**
     * 判断是否存在
     *
     * @param tableName
     * @return
     * @throws IOException
     */
    public static boolean isTableExist(String tableName) {
        // 在hbase中 先创建一个连接
        Connection connection = null;
        HBaseAdmin admin = null;
        try {
            connection = ConnectionFactory.createConnection(conf);

            admin = (HBaseAdmin) connection.getAdmin();
            return admin.tableExists(tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 判断表是不是存在
        return false;
    }


    /**
     * 创建表
     *
     * @param tableName    要创建的表名
     * @param columnFamily 列簇
     */
    public static void createTable(String tableName, String... columnFamily) {
        try {
            HBaseAdmin admin = new HBaseAdmin(conf);

            if (isTableExist(tableName)) {
                System.out.println("表" + tableName + "已经存在");
            } else {
                // 创建表对象，表名需要换类型
                HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(tableName));

                // 创建多个列簇
                for (String cf : columnFamily) {
                    descriptor.addFamily(new HColumnDescriptor(cf));
                }

                // 根据表的配置，创建表
                admin.createTable(descriptor);
                System.out.println(tableName + "创建成功！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 插入表
     *
     * @param tableName    表
     * @param rowKey       行键
     * @param columnFamily 列簇
     * @param cloumn       列
     * @param value        数据
     */
    public static void putTableData(String tableName, String rowKey, String columnFamily, String cloumn, String value) {
        // 创建Htable的对象
        try {
            HTable htable = new HTable(conf, tableName);

            // 创建一个put对象
            Put put = new Put(Bytes.toBytes(rowKey));
            // 向put对象中插入数据
            put.add(Bytes.toBytes(columnFamily), Bytes.toBytes(cloumn), Bytes.toBytes(value));
            // 将数据插入到表中
            htable.put(put);
            htable.close();

            System.out.println("数据插入成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 查询
     *
     * @param tablename
     */
    public static void scanTable(String tablename) {
        try {
            HTable htable = new HTable(conf, tablename);
            Scan scan = new Scan();
            ResultScanner scanner = htable.getScanner(scan);
            for (Result result : scanner) {
                Cell[] cells = result.rawCells();
                for (Cell cell : cells) {
                    System.out.print(Bytes.toString(CellUtil.cloneRow(cell)));
                    System.out.print("\tcolumn=" + Bytes.toString(CellUtil.cloneFamily(cell)) + ":" + Bytes.toString(CellUtil.cloneQualifier(cell)));
                    System.out.print("\ttimestamp=" + cell.getTimestamp());
                    System.out.print("\tvalue=" + Bytes.toString(CellUtil.cloneValue(cell)) + "\n");
                }
            }
            System.out.println("查询完毕；");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取某一行指定“列族:列”的数据
     *
     * @param tableName    表
     * @param rowKey       行键
     * @param columnFamily 列簇
     * @param qualifier    列
     * @throws IOException
     */
    public static void getRowQualifier(String tableName, String rowKey, String columnFamily, String qualifier) {
        HTable table = null;
        Get get = null;
        Result result = null;
        try {
            table = new HTable(conf, tableName);
            get = new Get(Bytes.toBytes(rowKey));
            get.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifier));
            result = table.get(get);
            for (Cell cell : result.rawCells()) {
                System.out.print(Bytes.toString(CellUtil.cloneRow(cell)));
                System.out.print("\tcolumn=" + Bytes.toString(CellUtil.cloneFamily(cell)) + ":" + Bytes.toString(CellUtil.cloneQualifier(cell)));
                System.out.print("\ttimestamp=" + cell.getTimestamp());
                System.out.print("\tvalue=" + Bytes.toString(CellUtil.cloneValue(cell)) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 删除行
     *
     * @param tablename
     * @param rows
     */
    public static void deleteMultiRow(String tablename, String... rows) {
        HTable htable = null;
        try {
            htable = new HTable(conf, tablename);
            List<Delete> deleteList = new ArrayList<Delete>();

            for (String row : rows) {
                Delete delete = new Delete(Bytes.toBytes(row));
                deleteList.add(delete);
            }
            htable.delete(deleteList);
            htable.close();
            System.out.println("删除完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除表
     *
     * @param tableName
     * @throws Exception
     */
    public static void dropTable(String tableName) throws Exception {
        HBaseAdmin admin = new HBaseAdmin(conf);
        if (isTableExist(tableName)) {
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
            System.out.println("表" + tableName + "删除成功！");
        } else {
            System.out.println("表" + tableName + "不存在！");
        }
    }


}
