package com.mapreduce.logclean1;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * $功能描述： logBean
 *
 * @author ：smart-dxw
 * @version ： 2019/6/12 21:18 v1.0
 */

@Getter
@Setter
@ToString

public class logBean {
    // 客户端的ip地址
    private String addr;

    // 客户端的用户名.属性忽略"-"
    private String user;

    // 用户访问的世家
    private String time;

    // 用户的url与http协议
    private String request;

    //是否访问成功的状态吗：大于等于400
    private String status;

    // 发送给客户端文件的大小
    private String size;

    // 记录用户从哪个网页访问的
    private String referer;

    // 记录客户端浏览器的信息
    private String user_agent;

    // 判断是否合法
    private boolean vail=true;

}
