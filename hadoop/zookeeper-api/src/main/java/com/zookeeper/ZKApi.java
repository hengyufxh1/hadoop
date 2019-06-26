package com.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * $功能描述： ZKApi
 *
 * @author ：smart-dxw
 * @version ： 2019/6/26 22:29 v1.0
 */
public class ZKApi {

//    private String connectname = "192.168.1.141:2181";
    private String connectname = "11.11.11.151:2181";

    private static int time = 2000;

    private ZooKeeper zkClient = null;

    {
        try {
            zkClient = new ZooKeeper(connectname, time, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    // 收到时间通知后的回调函数（用户的业务逻辑）
                    System.out.println(watchedEvent.getType());
                    System.out.println(watchedEvent.getPath());
                    System.out.println(watchedEvent.getState());
                    System.out.println(watchedEvent.getWrapper());
                    System.out.println(watchedEvent.toString());
                    // 再次启动监听器
                    try {
                        zkClient.getChildren("/", true);
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建
     *
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void create() {
        String a = null;
        try {
            a = zkClient.create("/dxw1", "aaa".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(a);
    }

    @Test
    public void ls() {
        try {
            List<String> children = zkClient.getChildren("/", true);
            children.forEach(a -> {
                System.out.println(a);
            });
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 是否存在
     */
    @Test
    public void isnode() {
        Stat exists = null;
        try {
            exists = zkClient.exists("/dxw", true);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(exists == null ? "不存在" : "存在");

    }


}
