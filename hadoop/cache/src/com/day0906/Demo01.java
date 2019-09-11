package com.day0906;

import net.spy.memcached.MemcachedClient;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * $功能描述： Demo01
 *
 * @author ：smart-dxw
 * @version ： 2019/9/6 21:19 v1.0
 */
public class Demo01 {

    public static void main(String[] args) {

        a();
        b();
    }


    /**
     * 保存
     */
    public static void a() {
        // 建立 MemcachedClient实例
        MemcachedClient client = null;
        try {
            client = new MemcachedClient(new InetSocketAddress("11.11.11.151", 11211));
            Future<Boolean> f = client.set("key2", 10, "Hello World");
            if (f.get().booleanValue()) {
                client.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取
     */
    public static void b() {
        // 建立 MemcachedClient实例
        MemcachedClient client = null;
        try {
            client = new MemcachedClient(new InetSocketAddress("11.11.11.151", 11211));
            Object object = client.get("key1");
            System.out.println("取到的值是：" + object);
            client.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 保存
     */
    public static void c() {
        // 建立 MemcachedClient实例
        MemcachedClient client = null;
        try {
            client = new MemcachedClient(new InetSocketAddress("11.11.11.151", 11211));
            Future<Boolean> f = client.set("key3", 0, new Student());
            if (f.get().booleanValue()) {
                client.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 客户端路由算法
     * 它会轮询挨个去写入
     */
    public static void d() {

        // 建立 MemcachedClient实例
        MemcachedClient client = null;
        List<InetSocketAddress> list  = new ArrayList<>();
        list.add(new InetSocketAddress("11.11.11.151", 11211));
        list.add(new InetSocketAddress("11.11.11.151", 11212));
        list.add(new InetSocketAddress("11.11.11.151", 11213));


        try {
            client = new MemcachedClient(list);
            for (int i = 0;i<20;i++){
                System.out.println("插入数据："+ i);
                client.set("key"+i,0,"value"+i);
                Thread.sleep(1000);
            }
            client.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


/**
 * Memcached可以写入自定类型，一定要保证这个自定义类型是序列化以后的。
 */
class Student implements Serializable {

}
