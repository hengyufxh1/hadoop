package com.mr_input;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * $功能描述： MyRecordReder
 *
 * @author ：smart-dxw
 * @version ： 2019/6/23 20:19 v1.0
 */
public class MyRecordReader extends RecordReader<NullWritable, BytesWritable> {

    private Configuration configuration;
    private FileSplit split;


    // 是否数据加工 processed 加工过的
    private boolean processed = false;

    private BytesWritable value = new BytesWritable();


    /**
     * 初始化
     * @param inputSplit
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void initialize(InputSplit inputSplit, TaskAttemptContext context) throws IOException, InterruptedException {
        // 1.初始化
        configuration = context.getConfiguration();
        // 加入到切分
        split = (FileSplit) inputSplit;
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        if(!processed){
            // 2.定义存储数据的缓冲区
            byte[] contents = new byte[(int) split.getLength()];
            FileSystem fs = null;
            FSDataInputStream fis = null;

            try {
                // 获取文件系统
                Path path = split.getPath();
                fs = path.getFileSystem(configuration);
                // 创建读数据的 流  读数据
                fis = fs.open(path);

                // 读取文件
                IOUtils.readFully(fis, contents,0,contents.length);

                // 写文件
                value.set(contents, 0, contents.length);
            }catch (Exception e){

            }finally {
                IOUtils.closeStream(fis);
//                if (fis != null)
//                    fis.close();
//                if (fs != null)
//                    fs.close();
            }
            // 不重复读取数据
            processed = true;
            return true;
        }
        return false;
    }

    @Override
    public NullWritable getCurrentKey() throws IOException, InterruptedException {
        return NullWritable.get();
    }

    @Override
    public BytesWritable getCurrentValue() throws IOException, InterruptedException {
        return value;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        return processed?1:0;
    }

    @Override
    public void close() throws IOException {

    }

}