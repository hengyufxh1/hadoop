import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * $功能描述： MyInterceptor
 *
 * @author ：smart-dxw
 * @version ： 2019/7/3 21:49 v1.0
 */
public class MyInterceptor implements Interceptor {
    @Override
    public void initialize() {
    }

    @Override
    public void close() {
    }

    /**
     * 拦截source发送到通道channel中的消息
     *
     * @param event 接收过滤的event
     * @return event    根据业务处理后的event
     */
    @Override
    public Event intercept(Event event) {
        // 获取事件对象中的字节数据
        byte[] arr = event.getBody();
        // 将获取的数据转换成大写
        event.setBody(new String(arr).toUpperCase().getBytes());
        // 返回到消息中
        return event;
    }
    // 接收被过滤事件集合
    @Override
    public List<Event> intercept(List<Event> events) {
        List<Event> list = new ArrayList<>();
        for (Event event : events) {
            list.add(intercept(event));
        }
        return list;
    }

    public static class Builder implements Interceptor.Builder {
        // 获取配置文件的属性
        @Override
        public Interceptor build() {
            return new MyInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
