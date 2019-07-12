package com.mapreduce.friends.mr2;

import com.mapreduce.Drive;
import com.mapreduce.friends.mr1.Friend;
import com.mapreduce.friends.mr1.MapFriend;
import com.mapreduce.friends.mr1.ReduceFriend;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

/**
 * $功能描述： Friend2
 *
 * @author ：smart-dxw
 * @version ： 2019/6/21 21:48 v1.0
 */
public class Friend2 {
    public static void main(String[] args) throws ClassNotFoundException, URISyntaxException, InterruptedException, IOException {
        args = new String[]{
                "E:\\bigdata\\hadoop\\_learn\\hadoop\\1demofile\\friend\\mr2\\in"
                ,"E:\\bigdata\\hadoop\\_learn\\hadoop\\1demofile\\friend\\mr2\\out" + new Date().getMinutes()
        };


        Drive.run(Friend.class,
                MapFriend.class,
                Text.class,
                Text.class,
                ReduceFriend.class,
                Text.class,
                Text.class,
                args[0],
                args[1]);


    }
}
