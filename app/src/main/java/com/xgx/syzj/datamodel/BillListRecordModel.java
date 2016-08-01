package com.xgx.syzj.datamodel;

import android.util.JsonReader;

import com.xgx.syzj.app.Api;
import com.xgx.syzj.base.BaseRequest;
import com.xgx.syzj.bean.BillGoodsDetailbean;
import com.xgx.syzj.bean.BillListItemBean;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.event.BaseDataEvent;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.widget.list.ListPageInfo;
import com.xgx.syzj.widget.list.PagedListDataModel;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sam on 2016/3/11 16:58.
 */
public class BillListRecordModel extends PagedListDataModel<BillGoodsDetailbean> {
    public static final byte DELETE_BILL_RECORD =  0x11;
    private static byte code;
    private BaseDataEvent<BillGoodsDetailbean>  billData = new BaseDataEvent<>();
    private List<BillGoodsDetailbean> goodsEntityList = new ArrayList<>();
    private int associatorId,flag,customerType;
    private String beginTime,endTime;
    private BillGoodsDetailbean GoodsDetailbean;

    public BillListRecordModel(int num,int associatorId, String begin, String end, int customerType, int flag) {
        mListPageInfo = new ListPageInfo<>(num);
        this.associatorId = associatorId;
        this.customerType = customerType;
        this.flag = flag;
        this.beginTime = begin;
        this.endTime = end;
    }
    //设置用户的类型： 普通消费者或：1，会员消费者 ：2，所有消费者 0;
    public void setCustomerType(){

    }
    public void setTime(String beginTime){
        this.beginTime = beginTime;
        goodsEntityList.clear();
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

//               billData.dataList = list;
               if(list != null && list.size() > 0){
                   for(int i = 0;i < list.size();i++){
                       for(int j = 0 ;j < list.get(i).getBillDetails().size();j++){
                           GoodsDetailbean = new BillGoodsDetailbean();
                           GoodsDetailbean.setBillDatetime(list.get(i).getBillDatetime());
                           GoodsDetailbean.setCustomerType(list.get(i).getCustomerType());
                           GoodsDetailbean.setBillDetailsId(list.get(i).getBillDetails().get(j).getBillDetailsId());
                           GoodsDetailbean.setBillId(list.get(i).getBillDetails().get(j).getBillId());
                           GoodsDetailbean.setCostPrice(list.get(i).getBillDetails().get(j).getCostPrice());
                           GoodsDetailbean.setFlag(list.get(i).getBillDetails().get(j).getFlag());
                           GoodsDetailbean.setProductId(list.get(i).getBillDetails().get(j).getProductId());
                           GoodsDetailbean.setQuantity(list.get(i).getBillDetails().get(j).getQuantity());
                           GoodsDetailbean.setReturnReason(list.get(i).getBillDetails().get(j).getReturnReason());
                           GoodsDetailbean.setSellingPrice(list.get(i).getBillDetails().get(j).getSellingPrice());
                           GoodsDetailbean.setTotalValue(list.get(i).getBillDetails().get(j).getTotalValue());
                           GoodsDetailbean.setStoreId(list.get(i).getBillDetails().get(j).getStoreId());
                           GoodsDetailbean.setProductName(list.get(i).getBillDetails().get(j).getProductName());
                           goodsEntityList.add(GoodsDetailbean);
                       }
                   }
                   billData.dataList = goodsEntityList;
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
               EventCenter.getInstance().post(goodsEntityList);
           }

           @Override
           public void onError(String errorCode, String message) {
               setRequestFail();
               EventCenter.getInstance().post(message);
           }
       });
    }


    public void getMemberPayRecord(int associatorId,String begin,String end,int customerType,int flag){
        Api.getBillExtcount(associatorId, begin, end, customerType, flag, new BaseRequest.OnRequestListener() {
            @Override
            public void onSuccess(Result result) {
                Map<String,Object> map = new HashMap<String, Object>();
                JsonReader reader;
                if(result.getResult() != null)
                try {
                    reader= new JsonReader(new StringReader(result.getResult()));
                    reader.beginObject();
                    while (reader.hasNext()){
                        String name = reader.nextName();
                            if(name.equals("count")){
                            map.put("count",reader.nextInt());
                        }else {
                            reader.skipValue();
                        }
                    }
                    reader.endObject();
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                EventCenter.getInstance().post(map);
            }

            @Override
            public void onError(String errorCode, String message) {
                EventCenter.getInstance().post(message);
            }
        });
    }
}
