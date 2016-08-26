package com.xgx.syzj.bean;

/**
 * Created by Administrator on 2016/8/26 0026.
 */
public class OrderDetail {
//           “payOrderType”:”1”/*订单详细类型*/
//            “payType”:”4”,/* 支付方式, 详情见API相关格式*/
//            “orderAmount”:230,/* 订单总金额, 含赠送*/
//            “fee”:200,/* 实际支付金额*/
//            “employee”:” 张三丰”/*接单员*/
//            “carType”:”SUV”,/* 会员车型*/
//            "orderAmount": 200,/*订单总价*/
//            "status": 200

    private String payOrderType;
    private String payType;
    private long orderAmount;
    private long fee;
    private String employee;
    private String carType;
    private int status;
    private long orderAmout;

    public OrderDetail(String payOrderType, String payType, long orderAmount, long fee, String employee, String carType, int status, long orderAmout) {
        this.payOrderType = payOrderType;
        this.payType = payType;
        this.orderAmount = orderAmount;
        this.fee = fee;
        this.employee = employee;
        this.carType = carType;
        this.status = status;
        this.orderAmout = orderAmout;
    }

    public long getOrderAmout() {
        return orderAmout;
    }

    public void setOrderAmout(long orderAmout) {
        this.orderAmout = orderAmout;
    }

    public String getPayOrderType() {
        return payOrderType;
    }

    public void setPayOrderType(String payOrderType) {
        this.payOrderType = payOrderType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
