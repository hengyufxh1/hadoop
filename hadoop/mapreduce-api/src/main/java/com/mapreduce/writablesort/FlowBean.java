package com.mapreduce.writablesort;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.hadoop.io.WritableComparable;

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
public class FlowBean implements WritableComparable<FlowBean> {

    private long upFlow;
    private long downFlow;
    private long sumFlow;

    public FlowBean() {

    }

    // 比较器
    @Override
    public int compareTo(FlowBean o) {
        return this.sumFlow>o.sumFlow?-1:1;
    }

    // 序列化
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(this.upFlow);
        dataOutput.writeLong(this.downFlow);
        dataOutput.writeLong(this.sumFlow);
    }

    // 反序列化
    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.upFlow=dataInput.readLong();
        this.downFlow=dataInput.readLong();
        this.sumFlow=dataInput.readLong();


    }
}
