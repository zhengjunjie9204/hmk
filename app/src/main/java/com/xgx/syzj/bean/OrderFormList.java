package com.xgx.syzj.bean;

import java.util.List;

/**
 * 订单列表，以日期为key，订单列表为value
 *
 * @author zajo
 * @created 2015年11月20日 11:23
 */
public class OrderFormList {

    private long data;

    private List<OrderForm> list;

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public List<OrderForm> getList() {
        return list;
    }

    public void setList(List<OrderForm> list) {
        this.list = list;
    }

}
