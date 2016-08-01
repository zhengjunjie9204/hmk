package com.xgx.syzj.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 消费记录
 *
 * @author zajo
 * @created 2015年09月21日 15:12
 */
public class Consumption implements Serializable {

    private int id;

    private String money_count;

    private String no_pay_count;

    private List<ConsumptionItem> data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMoney_count() {
        return money_count;
    }

    public void setMoney_count(String money_count) {
        this.money_count = money_count;
    }

    public String getNo_pay_count() {
        return no_pay_count;
    }

    public void setNo_pay_count(String no_pay_count) {
        this.no_pay_count = no_pay_count;
    }

    public List<ConsumptionItem> getData() {
        return data;
    }

    public void setData(List<ConsumptionItem> data) {
        this.data = data;
    }
}
