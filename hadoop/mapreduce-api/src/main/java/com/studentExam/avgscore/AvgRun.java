package com.studentExam.avgscore;

import com.Drive;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * $功能描述： run
 *
 * @author ：smart-dxw
 * @version ： 2019/6/19 22:02 v1.0
 */
public class AvgRun {
    public static void main(String[] args) throws ClassNotFoundException, URISyntaxException, InterruptedException, IOException {
        args = new String[]{
                "E:\\bigdata\\hadoop\\_learn\\hadoop\\1demofile\\studentExam\\01\\in",
                "E:\\bigdata\\hadoop\\_learn\\hadoop\\1demofile\\studentExam\\01\\AvgRun"
        };
        Drive.run(AvgRun.class,
                AvgMapper.class,
                Text.class,
                IntWritable.class,
                AvgReducer.class,
                Text.class,
                Text.class,
                args[0],
                args[1]);
    }
}
