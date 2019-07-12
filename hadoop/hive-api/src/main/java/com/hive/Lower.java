package com.hive;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * $功能描述： Lower
 *
 * @author ：smart-dxw
 * @version ： 2019/7/12 20:21 v1.0
 * hive 编写函数使用java 编译成jar包
 */
public class Lower extends UDF {

    public String evaluate (final String s) {

        if (s == null) {
            return null;
        }

        return s.toString().toLowerCase();
    }

}
