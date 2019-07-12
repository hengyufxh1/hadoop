package com.mapreduce.logclean;

import java.util.Date;

/**
 * $功能描述： LogDriver
 *
 * @author ：smart-dxw
 * @version ： 2019/6/12 20:49 v1.0
 */
class LogMain {

    public static void main(String[] args) {

        args[0] = new String("E:\\bigdata\\hadoop\\_learn\\hadoop\\1demofile\\logclean\\in");
        args[1] = new String("E:\\bigdata\\hadoop\\_learn\\hadoop\\1demofile\\logclean\\out"+new Date().getMinutes());



    }
}
