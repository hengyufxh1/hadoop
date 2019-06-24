package com.mr_input;


import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;

/**
 * $功能描述： MyInputFormat
 *
 * @author ：smart-dxw
 * @version ： 2019/6/23 20:19 v1.0
 */
public class MyInputFormat extends FileInputFormat<NullWritable, BytesWritable> {

    /**
     * 不需要切分 设置false
     * @param context
     * @param filename
     * @return
     */
    @Override
    protected boolean isSplitable(JobContext context, Path filename) {
        return false;
    }

    @Override
    public RecordReader<NullWritable, BytesWritable> createRecordReader(InputSplit inputSplit, TaskAttemptContext context) throws IOException, InterruptedException {
        MyRecordReader recordReader = new MyRecordReader();
        recordReader.initialize(inputSplit,context);
        return recordReader;
    }
}
