package com.xgx.syzj.datamodel;

import com.xgx.syzj.app.Api;
import com.xgx.syzj.base.BaseRequest;
import com.xgx.syzj.bean.BillListItemBean;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.event.BillEventPostData;
import com.xgx.syzj.ui.SaleHistoryActivity;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.widget.list.ListPageInfo;
import com.xgx.syzj.widget.list.PagedListDataModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by sam on 2016/3/11 16:58.
 */
public class SaleListRecordModel extends PagedListDataModel<BillListItemBean> {
    public static final byte DELETE_SALE_RECORD =  0x11;
    public static final byte UPDATA_SALE_RECORD =  0x12;
    private static byte code;
    private String TYPE = "";//请求类型，全部、会员、散客
    private BillEventPostData<BillListItemBean> billData = new BillEventPostData<>();
    private int associatorId = -1,flag = 0,customerType = -1;
    private String beginTime,endTime;
    public SaleListRecordModel(int num,String flag) {
        mListPageInfo = new ListPageInfo<>(num);
        this.TYPE = flag;
        billData.str = flag;
        if(!TYPE.isEmpty() && TYPE.equals(SaleHistoryActivity.SALE_MEMBER)){
            customerType = 1;
        }else if(!TYPE.isEmpty() && TYPE.equals(SaleHistoryActivity.SALE_GENERAL)){
            customerType = 0;
        }else if(!TYPE.isEmpty() && TYPE.equals(SaleHistoryActivity.SALE_ALL)){
            customerType = -1;
        }else {
           return;
        }
    }
    //设置用户的类型： 普通消费者或：1，会员消费者 ：2，所有消费者 0;
    public void setCustomerType(String  type){
        this.TYPE = type;
        billData.dataList = null;
        billData.hasMore = false;
        mListPageInfo = new ListPageInfo<>(mListPageInfo.getNumPerPage());
    }
    public void setTime(String beginTime,String endTime){
        this.beginTime = beginTime;
        this.endTime = endTime;
        billData.dataList = null;
        billData.hasMore = false;
        mListPageInfo = new ListPageInfo<>(mListPageInfo.getNumPerPage());
    }

    @Override
    protected void doQueryData() {
       Api.getBillTextList(associatorId,beginTime,endTime,customerType,flag,mListPageInfo.getPage(), mListPageInfo.getNumPerPage() ,new BaseRequest.OnRequestListener(){
           @Override
           public void onSuccess(Result result) {
               List<BillListItemBean> list = FastJsonUtil.json2List(result.getResult(),BillListItemBean.class);
               if(list.size() == 0)return;
               Iterator<BillListItemBean> it = list.iterator();
               while (it.hasNext()){
                   BillListItemBean bm = it.next();
                   if (bm.getBillDetails().size() == 0)
                       it.remove();
               }
              billData.dataList = list;
               if(list != null && list.size() > 0){
                   if(list.size() > mListPageInfo.getNumPerPage()){
                       billData.hasMore = true;
                   }else {
                       billData.hasMore = false;
                   }
               }else {
                   billData.hasMore = false;
                   list = new ArrayList<>();
               }
               setRequestResult(billData.dataList,billData.hasMore);
               EventBus.getDefault().post(billData);
           }

           @Override
           public void onError(String errorCode, String message) {
               setRequestFail();
               EventBus.getDefault().post(message);
           }
       });
    }

    public static void doRequst(int billId){
       Api.cancelBill(billId, new BaseRequest.OnRequestListener() {
           @Override
           public void onSuccess(Result result) {
               result.seteCode(DELETE_SALE_RECORD);
               EventBus.getDefault().post( result);
           }

           @Override
           public void onError(String errorCode, String message) {
               EventBus.getDefault().post(message);
           }
       });
    }

}
