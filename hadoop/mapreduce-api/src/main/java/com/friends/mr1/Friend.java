package com.friends.mr1;

import com.Drive;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

/**
 * $功能描述： Friend
 *
 * @author ：smart-dxw
 * @version ： 2019/6/21 21:44 v1.0
 */
public class Friend {
    public static void main(String[] args) throws ClassNotFoundException, URISyntaxException, InterruptedException, IOException {
        args = new String[]{
                "E:\\bigdata\\hadoop\\_learn\\hadoop\\1demofile\\friend\\mr1\\in"
                ,"E:\\bigdata\\hadoop\\_learn\\hadoop\\1demofile\\friend\\mr1\\out" + new Date().getMinutes()
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
