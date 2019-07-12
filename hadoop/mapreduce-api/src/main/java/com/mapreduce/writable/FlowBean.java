package com.mapreduce.writable;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * $功能描述： FlowBean
 *
 * @author ：smart-dxw
 * @version ： 2019/6/14 22:13 v1.0
 */

@ToString
@Getter
@Setter
public class FlowBean implements Writable {

    private long upFlow;
    private long downFlow;
    private long sumFlow;

    // 无参的构造方法 在反序列化的时候调用
    public FlowBean(){}

    public FlowBean(long upFlow,long downFlow){
        this.upFlow = upFlow;
        this.downFlow = downFlow;
        this.sumFlow = upFlow+downFlow;
    }

    // 序列化  这里的顺序要一致
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(upFlow);
        dataOutput.writeLong(downFlow);
        dataOutput.writeLong(sumFlow);
    }

    // 反序列化
    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.upFlow = dataInput.readLong();
        this.downFlow = dataInput.readLong();
        this.sumFlow = dataInput.readLong();
    }


}
