package com.xgx.syzj.datamodel;

import com.xgx.syzj.app.Api;
import com.xgx.syzj.event.EventCenter;

/**
 * Created by sam on 2016/3/17 14:11.
 */
public class BillGoodsReturnModel extends BaseDataModel{
    public static final byte NOTIFY_BILL_ITEMLIST = 0x01;
    public static final byte REMOVE_BILL_GOOODS = 0x02;

    public static void doRequest(int productId,int employeeId,int payOrderId,int count,String reason,double sum){
        code = NOTIFY_BILL_ITEMLIST;
        Api.returnSaleBillGoods(productId,employeeId,payOrderId,count,reason,sum,listener);
    }
}
