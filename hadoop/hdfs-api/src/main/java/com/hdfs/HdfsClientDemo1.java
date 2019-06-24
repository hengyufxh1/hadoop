package com.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


/**
 * $功能描述： HdfsClientDemo1
 *
 * @author ：smart-dxw
 * @version ： 2019/6/4 23:32 v1.0
 */
public class HdfsClientDemo1 {
    public static void main(String[] args) throws Exception {
        // 1 获取文件系统
        Configuration configuration = new Configuration();
        // 配置在集群上运行
        configuration.set("fs.defaultFS", "hdfs://asd1:9000");
        FileSystem fileSystem = FileSystem.get(configuration);

        // 直接配置访问集群的路径和访问集群的用户名称
//		FileSystem fileSystem = FileSystem.get(new URI("hdfs://bigdata111:9000"),configuration, "itstar");

        String pathIn = "E:\\bigdata\\hadoop\\_learn\\hadoop-hdfs-api\\src\\demo\\a.txt";
        String pathOut = "a2.txt";
        // 2 把本地文件上传到文件系统中
        fileSystem.copyFromLocalFile(new Path(pathIn), new Path(pathOut));

        // 3 关闭资源
        fileSystem.close();
        System.out.println("over");
    }



}
