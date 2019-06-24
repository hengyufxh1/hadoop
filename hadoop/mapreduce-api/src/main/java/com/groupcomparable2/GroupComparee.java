package com.groupcomparable2;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * $功能描述： GroupComparee
 *
 * @author ：smart-dxw
 * @version ： 2019/6/16 21:31 v1.0
 * 不要与源码类重名
 */
public class GroupComparee extends WritableComparator {

    protected  GroupComparee(){
        // true 开启辅助排序
        super(OrderBean.class,true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        OrderBean oa = (OrderBean) a;
        OrderBean ob = (OrderBean) b;

        int aa = oa.getId();
        int bb = ob.getId();

        if(aa>bb){
            return 1;
        }else if(aa>bb){
            return -1;
        }else{
            return oa.getPrice()>ob.getPrice()?-1:1;
        }
    }
}
