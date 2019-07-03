package com.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * $功能描述： SecondKill
 *
 * @author ：smart-dxw
 * @version ： 2019/6/28 20:10 v1.0
 */
public class SecondKill {

    // 定义共享资源，商品10个
    private static int num = 10;

    // 打印每次秒杀的结果
    public static void printNum(){
        // 获取线程的名字
        String name = Thread.currentThread().getName();

        System.out.println("********"+name+"**************");

        if(num>0){
            System.out.println("被抢走的商品"+num);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            num--;
        }else{
            System.out.println("手慢了，商品已经被抢光了");
        }
    }

    public static void main(String[] args) {
        // 定义zk客户端重试策略
        ExponentialBackoffRetry retry = new ExponentialBackoffRetry(1000, 10);
        // 构建一个客户端
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("11.11.11.151:2181").retryPolicy(retry).build();
        System.out.println(client);
        // 构建zk锁 -> 在zk里面创建一个锁的目录
        final InterProcessMutex lock = new InterProcessMutex(client, "/sk");
        System.out.println();

        for(int i= 0;i<20;i++){
            new Thread(() -> {
                try {
                    // 枷锁
                    lock.acquire();
                    printNum();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    try {
                        // 解锁 释放锁
                        lock.release();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }



    }
}
