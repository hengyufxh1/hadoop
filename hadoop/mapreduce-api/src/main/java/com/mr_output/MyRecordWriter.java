package com.mr_output;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * $功能描述： MyRecordWriter
 *
 * @author ：smart-dxw
 * @version ： 2019/6/23 21:40 v1.0
 */
public class MyRecordWriter extends RecordWriter<Text, NullWritable> {

    FSDataOutputStream itstarOut = null;
    FSDataOutputStream otherOut = null;

    public MyRecordWriter(TaskAttemptContext context) {

        // 获取job里面传递的输出目录
        Path outputPath = FileOutputFormat.getOutputPath(context);

        FileSystem fs = null;
        try {
            fs = FileSystem.get(context.getConfiguration());
            itstarOut = fs.create(new Path(outputPath, "itstar.txt"));
            otherOut = fs.create(new Path(outputPath, "other.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(Text text, NullWritable nullWritable) throws IOException {
        // 是否抱哈 itstar 字段
        if (text.toString().contains("itstar")) {
            itstarOut.write(text.toString().getBytes());
        } else {
            otherOut.write(text.toString().getBytes());
        }
    }

    @Override
    public void close(TaskAttemptContext context) throws IOException {
        if (itstarOut != null)
            itstarOut.close();
        if (otherOut != null)
            otherOut.close();

    }
}
