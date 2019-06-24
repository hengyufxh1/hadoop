import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.CompressionInputStream;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * $功能描述： Testcompress
 *
 * @author ：smart-dxw
 * @version ： 2019/6/16 22:41 v1.0
 */
public class TestCompress {

    private static String path1 = "E:\\bigdata\\hadoop\\_learn\\hadoop\\1demofile\\compress\\01\\phone.txt";
    private static String path2 = "E:\\bigdata\\hadoop\\_learn\\hadoop\\1demofile\\compress\\01\\phone.txt.gz";
    private static String path3 = "E:\\bigdata\\hadoop\\_learn\\hadoop\\1demofile\\compress\\01\\phone.txt.bz2";

    public static void main(String[] args) throws Exception {
        compress(path1, "org.apache.hadoop.io.compress.GzipCodec");
        decompression(path2,"txt");
        compress(path1, "org.apache.hadoop.io.compress.BZip2Codec");
        decompression(path3,"txt");
        System.out.println("ok");
    }

    /**
     * 压缩方法
     */
    public static void compress(String filename, String method) throws Exception {
        // 创建输入流
        FileInputStream fis = new FileInputStream(new File(filename));
        // 通过反射找到编码 / 解码器
        Class codeClass = Class.forName(method);
        // 通过工具类获取解码/解码器的对象，同时还需要传入Hadoop的配置
        CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(codeClass, new Configuration());
        // 创建一个输出流
        FileOutputStream fos = new FileOutputStream(new File(filename + codec.getDefaultExtension()));
        // 解码器流
        CompressionOutputStream cos = codec.createOutputStream(fos);
        // 对接流
        IOUtils.copyBytes(fis, cos, 1024 * 1024 * 5, false);
        //关闭流
        fis.close();
        cos.close();
        fos.close();
    }

    /**
     * 解压
     * @param filename
     * @param decoded
     * @throws IOException
     */
    public static void decompression(String filename, String decoded) throws IOException {
        //获取实例
        CompressionCodecFactory factory = new CompressionCodecFactory(new Configuration());
        CompressionCodec codec = factory.getCodec(new Path(filename));
        // 解压是输入路径
        CompressionInputStream cis = codec.createInputStream(new FileInputStream(new File(filename)));
        // 输出流
        FileOutputStream fos = new FileOutputStream(new File(filename+"."+decoded));
        // 对接流
        IOUtils.copyBytes(cis, fos, 1024 * 1024 * 5, false);
        cis.close();
        fos.close();
    }
}
