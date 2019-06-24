package com.Exercise01;

import lombok.Getter;
import lombok.Setter;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author dxw
 * @version 1.0 2019年6月11日 00:39:32 v1.0
 *
 * 封装的数据类型需要怎么做 hadoop数据类型实现了序列化接口 如果自定义需要实现中国序列化接口
 */
@Getter
@Setter
public class ClassBean implements WritableComparable<ClassBean> {

	private String name;
	private String clas;

	public ClassBean(){};

	// 序列化
	public void write(DataOutput out) throws IOException {
		out.writeUTF(name);
		out.writeUTF(clas);
	}

	// 反序列化
	public void readFields(DataInput in) throws IOException {
		name = in.readUTF();
		clas = in.readUTF();
	}

	@Override
	public String toString() {
		return name + "\t" + clas;
	}

	@Override
	public int compareTo(ClassBean o) {
		return -1;
	}
}
