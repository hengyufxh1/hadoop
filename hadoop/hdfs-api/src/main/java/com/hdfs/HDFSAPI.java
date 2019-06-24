package com.hdfs;



import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.htrace.commons.logging.Log;
import org.apache.htrace.commons.logging.LogFactory;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;

/**
 * $功能描述： HDFSAPI
 *
 * @author ：smart-dxw
 * @version ： 2019/6/4 23:43 v1.0
 */
public class HDFSAPI {
    private static final Log logger = LogFactory.getLog(HDFSAPI.class);

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * 获取hdfs
     * @throws IOException
     */
    @Test
    public void initHDFS() throws IOException {
        // 创建配置信息
        Configuration conf = new Configuration();
        // 获取 文件系统 需要抛异常
        FileSystem fs = FileSystem.get(conf);
        System.out.println(fs.toString());
    }

    /**
     * 上传文件
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    @Test
    public void putHDFS() throws IOException, URISyntaxException, InterruptedException {
        // 创建配置信息
        Configuration conf = new Configuration();
        // 设置部分的临时参数
        conf.set("dfs.replication","2");

        // 获取文件系统
        FileSystem fs = FileSystem.get(new URI("hdfs://asd1:9000"), conf, "root");

        // windows要上传文件的路径
        Path input = new Path("E:\\bigdata\\hadoop\\_learn\\hadoop-hdfs-api\\src\\demo\\a.txt");

        // 输出的路径 input 赋值 output
        Path output = new Path("hdfs://asd1:9000/temp/a1.txt");

        fs.copyFromLocalFile(input,output);
        fs.close();
        System.out.println("上传成功！");
    }

    /**
     * 下载文件
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void getHDFS() throws URISyntaxException, IOException, InterruptedException {
        // 创建配置信息
        // ctrl + alt + v 后推前
        Configuration conf = new Configuration();
        // 获取文件系统
        FileSystem fs = FileSystem.get(new URI("hdfs://asd1:9000"), conf, "root");
        // 下载文件
        // 1.是不是删除原文件
        // 2.hdfs下载的路径
        // 3.windows的路径
        // 4.是否效验文件
        fs.copyToLocalFile(false
                , new Path("hdfs://asd1:9000/temp/a.txt")
                , new Path("E:\\bigdata\\hadoop\\_learn\\hadoop-hdfs-api\\src\\demo\\out\\2246.txt")
                , true);
        // 关闭资源
        fs.close();
        System.out.println("下载成功！");
    }

    /**
     * 创建目录
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void mkdirHDFS() throws URISyntaxException, IOException, InterruptedException {
        // 创建配置信息
        // ctrl + alt + v 后推前
        Configuration conf = new Configuration();

        // 获取文件系统
        FileSystem fs = FileSystem.get(new URI("hdfs://asd1:9000"), conf, "root");

        String mulu = "bb";
        // 创建目录
        fs.mkdirs(new Path("hdfs://asd1:9000/"+ mulu));

        fs.close();

        System.out.println("创建"+mulu+"目录成功");
    }


    /**
     * 删除目录
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void deleteHDFS() throws URISyntaxException, IOException, InterruptedException {
        // 创建配置信息
        // ctrl + alt + v 后推前
        Configuration conf = new Configuration();

        // 获取文件系统
        FileSystem fs = FileSystem.get(new URI("hdfs://asd1:9000"), conf, "root");

        String mulu = "bb";
        // 删除文件
        // 1.删除的路径
        // 2.是否进行递归删除
        fs.delete(new Path("hdfs://asd1:9000/"+ mulu),true);

        fs.close();

        System.out.println("删除"+mulu+"目录成功");
    }


    /**
     * 修改名称
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void renameHDFS() throws URISyntaxException, IOException, InterruptedException {
        // 创建配置信息
        // ctrl + alt + v 后推前
        Configuration conf = new Configuration();

        // 获取文件系统
        FileSystem fs = FileSystem.get(new URI("hdfs://asd1:9000"), conf, "root");

        String mulua = "aa";
        String mulub = "cc";
        // 重命名
        fs.rename(new Path("hdfs://asd1:9000/"+mulua), new Path("hdfs://asd1:9000/"+mulub));
        fs.close();

        System.out.println("已将"+mulua+"目录改为"+mulub+"！");
    }

    /**
     * 获取文件信息
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void readFile()throws URISyntaxException, IOException, InterruptedException {
        // 创建配置信息
        // ctrl + alt + v 后推前
        Configuration conf = new Configuration();

        // 获取文件系统
        FileSystem fs = FileSystem.get(new URI("hdfs://asd1:9000"), conf, "root");

        // 迭代器
//        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("hdfs://asd1:9000/temp"), false);
//        while (listFiles.hasNext()){
//            LocatedFileStatus fileStatus = listFiles.next();
//            // 权限 角色 组 长度 文件名字
//            System.out.println(fileStatus.getPermission()+" "+fileStatus.getOwner()+" "+fileStatus.getGroup()+"      "+fileStatus.getLen()+" "+sdf.format(fileStatus.getAccessTime())+" "+ fileStatus.getPath().getName());
//        }


        FileStatus[] fst = fs.listStatus(new Path("/temp"));
        for (FileStatus f :fst){
            System.out.println(f.getPermission()+" "+f.getOwner()+" "+f.getGroup()+"      "+f.getLen()+" "+sdf.format(f.getAccessTime())+" "+ f.getPath().getName());
        }

        fs.close();
    }


    /**
     * 根据IO流上传
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws IOException
     */
    @Test
    public void pubFileHDFS() throws URISyntaxException, InterruptedException, IOException {

        // 创建配置信息
        // ctrl + alt + v 后推前
        Configuration conf = new Configuration();

        // 获取文件系统
        FileSystem fs = FileSystem.get(new URI("hdfs://asd1:9000"), conf, "root");

        // 创建一个输入流
        FileInputStream fis = new FileInputStream("E:\\bigdata\\hadoop\\_learn\\hadoop-hdfs-api\\src\\demo\\a.txt");

        Path path = new Path("hdfs://asd1:9000/temp/a4.txt");


        FSDataOutputStream fos = fs.create(path);

        try {
            IOUtils.copyBytes(fis,fos,4*1024);
        }catch (IOException e){

        }finally {
            IOUtils.closeStream(fos);
            IOUtils.closeStream(fis);
            fs.close();
            System.out.println("传输完毕");
        }
    }

    /**
     * IO读取HDFS到控制台
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws IOException
     */
    @Test
    public void getFileHDFS() throws URISyntaxException, InterruptedException, IOException {
        //1.创建配置文件信息
        Configuration conf = new Configuration();

        //2.获取文件系统
        FileSystem fs = FileSystem.get(new URI("hdfs://asd1:9000"), conf, "root");

        //3.读取路径
        Path readPath = new Path("hdfs://asd1:9000/temp/a.txt");

        //4.输入
        FSDataInputStream fis = fs.open(readPath);

        //5.输出到控制台
        //InputStream in    输入
        //OutputStream out  输出
        //int buffSize      缓冲区
        //boolean close     是否关闭流
        IOUtils.copyBytes(fis,System.out,4 * 1024 ,true);

        fs.close();
    }

    /**
     * 下载
     * IO读取第一块的内容
     * @throws Exception
     */
    @Test
    public void  readFlieSeek1() throws Exception {
        //1.创建配置文件信息
        Configuration conf = new Configuration();

        //2.获取文件系统
        FileSystem fs = FileSystem.get(new URI("hdfs://asd1:9000"),conf,"root");

        //3.输入
        Path path = new Path("hdfs://asd1:9000/temp/a.txt");

        FSDataInputStream fis = fs.open(path);
        //4.输出
        FileOutputStream fos = new FileOutputStream("E:\\bigdata\\hadoop\\_learn\\hadoop-hdfs-api\\src\\demo\\out\\0031.txt");

        //5.流对接
        byte[] buf = new byte[1024];
        for (int i = 0; i < 128 * 1024; i++) {
            fis.read(buf);
            fos.write(buf);
        }

        //6.关闭流
        IOUtils.closeStream(fos);
        IOUtils.closeStream(fs);
        fs.close();
        System.out.println("下载完毕！");
    }

    /**
     * 系在
     * IO读取第二块的内容
     *
     * @throws Exception
     */
    @Test
    public void readFlieSeek2() throws Exception {
        //1.创建配置文件信息
        Configuration conf = new Configuration();

        //2.获取文件系统
        FileSystem fs = FileSystem.get(new URI("hdfs://asd1:9000"), conf, "root");

        //3.输入
        Path path = new Path("hdfs://asd1:9000/temp/a4.txt");
        FSDataInputStream fis = fs.open(path);

        //4.输出
        FileOutputStream fos = new FileOutputStream("E:\\bigdata\\hadoop\\_learn\\hadoop-hdfs-api\\src\\demo\\out\\2148.txt");

        //5.定位偏移量/offset/游标/读取进度 (目的：找到第一块的尾巴，第二块的开头)
        fis.seek(1);

        //6.流对接
        IOUtils.copyBytes(fis, fos, 1024);

        //7.关闭流
        IOUtils.closeStream(fos);
        IOUtils.closeStream(fis);
    }




//3）合并文件
//在window命令窗口中执行
//type A2 >> A1  然后更改后缀为rar即可
























}
