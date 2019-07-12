package com.mapreduce.writablesort2;


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

    private int id;
    private double price;

    public FlowBean() {

    }

    // 比较器
    @Override
    public int compareTo(FlowBean o) {

        // -1 不交换位置（负数）
        //  1 交换位置(正数)
        int a = this.id;

        int b = this.id;

        if(a > b){
            return 1;
        }else if(a<b){
            return -1;
        }else {
            return this.price>o.price?-1:1;
        }
    }

    // 序列化
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.id);
        dataOutput.writeDouble(this.price);
    }

    // 反序列化
    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id = dataInput.readInt();
        this.price = dataInput.readDouble();
    }
}
