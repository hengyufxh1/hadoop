package com.mapreduce.phonepartition;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

/**
 * $功能描述： PhoneNumUtils
 *
 * @author ：smart-dxw
 * @version ： 2019/6/14 23:05 v1.0
 */
public class PhoneNumRunnableUtils {

    private static final Logger logger = Logger.getLogger(PhoneNumRunnableUtils.class);


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 增加的数量、电话数组、生成是数量、类型
        Set<String> a = createPhone(0, PhonePartition.YD, 200, "我是移动");
        Set<String> b = createPhone(0, PhonePartition.DX, 200, "我是电信");
        Set<String> c = createPhone(0, PhonePartition.LT, 200, "我是联通");
        // Set合并
        merge(a, b);
        merge(a, c);
        // 写电话到文件
        writePhone(a);
    }

    private static Set<String> createPhone(long count, String[] phone, long num, String type) {
        Set<String> set = new TreeSet<>();
        while (count < num) {
            count++;
            int index = getNum(0, phone.length - 1);
            String a = phone[index];
            // 这里是获取随机数 +10000 防止不足4位 五位数截取第一位
            String b = String.valueOf(getNum(1, 9999) + 10000).substring(1);
            String c = String.valueOf(getNum(1, 9999) + 10000).substring(1);
            set.add(a + b + c);
        }
        return set;
    }

    public static int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }


    private static Set<String> merge(Set<String> a, Set<String> b) {
        for (String bStr : b) {
            a.add(bStr);
        }
        return a;
    }

    private static void writePhone(Set<String> a) {
        // 哟 20万发现重复了
        File file = new File("E:\\bigdata\\hadoop\\_learn\\hadoop\\demofile\\06142151\\in\\phone.txt");
        int writeNum = 0;
        for (String value : a) {
            writeNum++;
            FileWriter fw = null;
            try {
                fw = new FileWriter(file, true);
                if (writeNum % 5 == 0) {
                    fw.write(value + "\n");
                } else {
                    fw.write(value + "\t");
                }
            } catch (IOException e) {
                logger.error(e);
            } finally {
                try {
                    if (fw != null) {
                        fw.flush();
                        fw.close();
                    }
                } catch (IOException e) {
                    logger.error(e);
                }
            }
        }
    }
}















//    // 增加数量
//    private long count;
//    // 生成的数量
//    private long num;
//    // 电话号码
//    private String[] phone;
//    // 运营商类型
//    private String type;
//
//    public PhoneNumRunnableUtils(long count, String[] phone, long num, String type) {
//        this.count = count;
//        this.num = num;
//        this.phone = phone;
//        this.type = type;
//    }
//
//
//    @Override
//    public void run() {

//        Set<String> set = new TreeSet<>();
//        while (count < num) {
//            count++;
//            int index = getNum(0, phone.length - 1);
//            String a = phone[index];
//            String b = String.valueOf(getNum(1, 9999) + 10000).substring(1);
//            String c = String.valueOf(getNum(1, 9999) + 10000).substring(1);
//            set.add(a + b + c);
//        }
//        // 哟 20万发现重复了
//        System.out.println(set.size());
//        File file = new File("E:\\bigdata\\hadoop\\_learn\\hadoop\\demofile\\06142151\\in\\phone.txt");
//        int writeNum = 0;
//        for (String value : set) {
//            writeNum++;
//            FileWriter fw = null;
//            try {
//                fw = new FileWriter(file, true);
//                if (writeNum % 2 == 0) {
//                    fw.write(value + "\n");
//                } else {
//                    fw.write(value + "\t");
//                }
//            } catch (IOException e) {
//                logger.error(e);
//            } finally {
//                try {
//                    if (fw != null) {
//                        fw.flush();
//                        fw.close();
//                    }
//                } catch (IOException e) {
//                    logger.error(e);
//                }
//            }
//        }
//    }
//}
