package com.mapreduce.logclean1;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * $功能描述： LogMapper
 *
 * @author ：smart-dxw
 * @version ： 2019/6/12 20:48 v1.0
 */
public class LogMapper extends Mapper<LongWritable, Text, NullWritable, Text> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 获取数据
        String line = value.toString();

        // 解析日志
        logBean log = parseLog(line);

        // 判断是不是true
        if (log.isVail()) {
            context.write(NullWritable.get(), new Text(log.toString()));
        }

        // 输出
    }

    private logBean parseLog(String line) {
        logBean logBean = new logBean();
        String[] fields = line.split(" ");

        if (fields.length > 11) {
            logBean.setAddr(fields[0]);
            logBean.setUser(fields[1]);
            logBean.setTime(fields[3].substring(1));
            logBean.setRequest(fields[6]);
            logBean.setStatus(fields[8]);
            logBean.setSize(fields[9]);
            logBean.setReferer(fields[10]);
            if (fields.length > 12) {
                logBean.setUser_agent(fields[11] + fields[12]);
            } else {
                logBean.setUser_agent(fields[11]);
            }

            if (Integer.parseInt(logBean.getStatus()) >= 400) {
                logBean.setVail(false);
            }
        } else {
            logBean.setVail(false);
        }

        return logBean;
    }
}
