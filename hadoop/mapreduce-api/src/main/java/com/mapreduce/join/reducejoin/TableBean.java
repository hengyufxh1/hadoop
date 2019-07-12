package com.mapreduce.join.reducejoin;

import lombok.Getter;
import lombok.Setter;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * $功能描述： TableBean
 *
 * @author ：smart-dxw
 * @version ： 2019/6/17 20:12 v1.0
 */

@Getter
@Setter
public class TableBean implements Writable {
    //  订单id
    private String order_id;
    // 产品id
    private String p_id;
    // 产品数量
    private int amount;
    // 产品名称
    private String pname;
    // 表的标记
    private String flag;

    public TableBean() {

    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.order_id);
        dataOutput.writeUTF(this.p_id);
        dataOutput.writeInt(this.amount);
        dataOutput.writeUTF(this.pname);
        dataOutput.writeUTF(this.flag);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.order_id = dataInput.readUTF();
        this.p_id = dataInput.readUTF();
        this.amount = dataInput.readInt();
        this.pname = dataInput.readUTF();
        this.flag = dataInput.readUTF();
    }


    @Override
    public String toString() {
        return order_id  +"\t" + p_id  + "\t" + amount +"\t" + pname;
    }
}
