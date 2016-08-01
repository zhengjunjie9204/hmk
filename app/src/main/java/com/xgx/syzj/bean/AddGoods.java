package com.xgx.syzj.bean;

import java.io.Serializable;

/**增加商品
 * Created by Administrator on 2016/7/29 0029.
 */
public class AddGoods implements Serializable {

    private String strCode;
    private String strName;
    private String strBrand;
    private String strType;
    private String strInputMoney;
    private String strSellMoney;
    private String strInputCount;

    @Override
    public String toString() {
        return "AddGoods{" +
                "strCode='" + strCode + '\'' +
                ", strName='" + strName + '\'' +
                ", strBrand='" + strBrand + '\'' +
                ", strType='" + strType + '\'' +
                ", strInputMoney='" + strInputMoney + '\'' +
                ", strSellMoney='" + strSellMoney + '\'' +
                ", strInputCount='" + strInputCount + '\'' +
                ", strUnit='" + strUnit + '\'' +
                '}';
    }

    public AddGoods(String strCode, String strName, String strBrand, String strType, String strInputMoney, String strSellMoney, String strInputCount, String strUnit) {
        this.strCode = strCode;
        this.strName = strName;
        this.strBrand = strBrand;
        this.strType = strType;
        this.strInputMoney = strInputMoney;
        this.strSellMoney = strSellMoney;
        this.strInputCount = strInputCount;
        this.strUnit = strUnit;
    }

    public String getStrCode() {
        return strCode;
    }

    public void setStrCode(String strCode) {
        this.strCode = strCode;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public String getStrBrand() {
        return strBrand;
    }

    public void setStrBrand(String strBrand) {
        this.strBrand = strBrand;
    }

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }

    public String getStrInputMoney() {
        return strInputMoney;
    }

    public void setStrInputMoney(String strInputMoney) {
        this.strInputMoney = strInputMoney;
    }

    public String getStrSellMoney() {
        return strSellMoney;
    }

    public void setStrSellMoney(String strSellMoney) {
        this.strSellMoney = strSellMoney;
    }

    public String getStrInputCount() {
        return strInputCount;
    }

    public void setStrInputCount(String strInputCount) {
        this.strInputCount = strInputCount;
    }

    public String getStrUnit() {
        return strUnit;
    }

    public void setStrUnit(String strUnit) {
        this.strUnit = strUnit;
    }

    private String strUnit;
}
