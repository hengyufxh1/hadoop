package com.flume;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * $功能描述： MyConversion
 *
 * @author ：smart-dxw
 * @version ： 2019/7/3 22:03 v1.0
 */
public class MyConversion implements Interceptor {
    @Override
    public void initialize() {
        System.out.println("初始化"+new Date().getTime());
    }

    @Override
    public Event intercept(Event event) {
        byte[] body = event.getBody();
        event.setBody(new String(body).toUpperCase().getBytes());
        return event;
    }

    @Override
    public List<Event> intercept(List<Event> list) {
        List events = new ArrayList<Event>();
        for (Event e: list){
            events.add(intercept(e));
        }
        return events;
    }

    @Override
    public void close() {

    }

    /**
     * 内部类
     */
    public static class Builder implements Interceptor.Builder {
        // 获取配置文件的属性
        @Override
        public Interceptor build() {
            return new MyConversion();
        }

        @Override
        public void configure(Context context) {

        }
    }
}