package com.groupcomparable2;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * $功能描述： OrderBean
 *
 * @author ：smart-dxw
 * @version ： 2019/6/16 21:27 v1.0
 */

@Setter
@Getter
@ToString
public class OrderBean implements WritableComparable<OrderBean> {
    private int id;
    private double price;

    @Override
    public int compareTo(OrderBean o) {
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

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.id);
        dataOutput.writeDouble(this.price);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id = dataInput.readInt();
        this.price = dataInput.readDouble();
    }
}
