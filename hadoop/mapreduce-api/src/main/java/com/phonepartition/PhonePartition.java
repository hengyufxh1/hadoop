package com.phonepartition;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.log4j.Logger;

import java.util.Arrays;

/**
 * $功能描述： PhonePartition
 *
 * @author ：smart-dxw
 * @version ： 2019/6/14 21:21 v1.0
 */
public class PhonePartition extends Partitioner<Text, NullWritable> {

    private static final Logger logger = Logger.getLogger(PhonePartition.class);

    // 移动
    public static final String[] YD = {
            "134", "135", "136"
            , "137", "138", "139"
            , "150", "151", "152"
            , "157", "158", "159"
            , "188", "187", "182"
            , "183", "184", "178"
            , "147", "172", "198"

    };

    // 电信
    public static final String[] DX = {
            "133", "149", "153"
            , "173", "177", "180"
            , "181", "189", "199"
    };

    // 联通
    public static final String[] LT = {
            "130", "131", "132"
            , "145", "155", "156"
            , "166", "171", "175"
            , "176", "185", "186"
            , "166"
    };

    @Override
    public int getPartition(Text text, NullWritable nullWritable, int i) {

        // 获取数据
        String fields = text.toString();
        // 截取电话号码的前三位
        String substring = fields.substring(0, 3);

        // 判断是那个分区的

        if (Arrays.asList(YD).contains(substring)) {
            return 0;
        } else if (Arrays.asList(DX).contains(substring)) {
            return 1;
        } else if (Arrays.asList(LT).contains(substring)) {
            return 2;
        }
        return 3;
    }
}
