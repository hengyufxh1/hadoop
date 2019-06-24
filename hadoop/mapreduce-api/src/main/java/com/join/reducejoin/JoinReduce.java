package com.join.reducejoin;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * $功能描述： JoinReduce
 *
 * @author ：smart-dxw
 * @version ： 2019/6/17 20:12 v1.0
 */
public class JoinReduce extends Reducer<Text, TableBean, TableBean, NullWritable> {
    @Override
    protected void reduce(Text key, Iterable<TableBean> values, Context context) throws IOException, InterruptedException {
        // 创建集合存储订单表的对象
        ArrayList<TableBean> orderbeans = new ArrayList<TableBean>();

        // 存储产品对象
        TableBean pdbean = new TableBean();

        for (TableBean bean: values){
            // 判断是否是订单表
            if("0".equals(bean.getFlag())){
                // 定义一个存储order.txt 的对象
                TableBean orderbean = new TableBean();

                try {
                    BeanUtils.copyProperties(orderbean,bean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                orderbeans.add(orderbean);
                System.out.println("============分隔符================");
            }else {
                // 拷贝
                try {
                    BeanUtils.copyProperties(pdbean,bean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("============分隔符================");
            for (TableBean bean2 : orderbeans){
                // 将产品表里面的名字传到定点表里面
                bean2.setPname(pdbean.getPname());

                // 数据输出
                context.write(bean2,NullWritable.get());
            }
        }
    }
}
