package com.xgx.syzj.datamodel;

import android.util.JsonReader;

import com.xgx.syzj.app.Api;
import com.xgx.syzj.app.Url;
import com.xgx.syzj.base.BaseRequest;
import com.xgx.syzj.bean.AnalySaleTotal;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.ui.AnalysisActivity;
import com.xgx.syzj.utils.FastJsonUtil;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by sam on 2016/3/18 17:14.
 */
public class BusinessSaleAnalyModel{

    public static final byte SALE_ANALY_GROSSPROFIT = 0x06;
    public static final byte SALE_ANALY_GROSSSALES = 0x05;


    //报表分析：销售分析 毛利总额
    public static void getSalesAnalyTotal(String begin, String end, int page, int pageSize) {
            Api.getSalesAnalyTotal(Url.ANALY_SALE_GROSSPROFIT, begin, end, page, pageSize, new BaseRequest.OnRequestListener() {
                @Override
                public void onSuccess(Result result) {
                    result.seteCode(AnalysisActivity.SALE_ANALY_TOTAL);
                    EventBus.getDefault().post(result);
                }

                @Override
                public void onError(String errorCode, String message) {

                }
            });
    }

    //报表分析：销售分析 营销总额
    public static void getSaleAnalyGrosssales(String begin, String end, int page, int pageSize) {

        Api.getSalesAnalyGrossales(Url.ANALY_SALE_GROSSSALES, begin, end, page, pageSize, new BaseRequest.OnRequestListener() {
            @Override
            public void onSuccess(Result result) {
                    JsonReader reader;
                    Map<String, String> map = new HashMap<String, String>();
                    if(result.getResult() != null)
                        try {
                            reader= new JsonReader(new StringReader(result.getResult()));
                            reader.beginObject();
                            while (reader.hasNext()){
                                String name = reader.nextName();
                                if(name.equals("totalValue")){
                                    double d = reader.nextDouble();
                                    map.put("totalValue",""+d);
                                    EventBus.getDefault().post(map);
                                }else {
                                    reader.skipValue();
                                }
                            }
                            reader.endObject();
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            @Override
            public void onError(String errorCode, String message) {

            }
        });
    }


    //报表分析:会员分析
    public static void getMemberAnalyTotal(String begin, String end, int page, int pageSize) {
        Api.getSalesAnalyTotal(Url.ANALY_SALE_TTOTAL_CONSUMPTION, begin, end, page, pageSize, new BaseRequest.OnRequestListener() {
            @Override
            public void onSuccess(Result result) {
                result.seteCode(AnalysisActivity.ASSOCIOR_SURVEY_CONSUME);
                EventBus.getDefault().post(result);
            }

            @Override
            public void onError(String errorCode, String message) {

            }
        });
    }

    //报表分析:营销分析  销售笔数最高
    public static void getaleAnalyCount(String begin, String end, int page, int pageSize) {
        Api.getSalesAnalyTotal(Url.ANALY_SALE_COUNT, begin, end, page, pageSize, new BaseRequest.OnRequestListener() {
            @Override
            public void onSuccess(Result result) {
                result.seteCode(AnalysisActivity.SALE_ANALY_COUNT);
                List<AnalySaleTotal> list = FastJsonUtil.json2List(result.getResult(),AnalySaleTotal.class);
                EventBus.getDefault().post(list);
            }

            @Override
            public void onError(String errorCode, String message) {
                EventBus.getDefault().post(message);
            }
        });
    }



    //报表分析:营销分析 销售额最高
    public static void getaleAnalyTotals(String begin, String end, int page, int pageSize) {
        Api.getSalesAnalyTotal(Url.ANALY_SALE_TOTAL, begin, end, page, pageSize, new BaseRequest.OnRequestListener() {
            @Override
            public void onSuccess(Result result) {
                result.seteCode(AnalysisActivity.SALE_ANALY_TOP_TOTAL);
                EventBus.getDefault().post(result);
            }

            @Override
            public void onError(String errorCode, String message) {
                EventBus.getDefault().post(message);
            }
        });
    }


    //报表分析:营销分析 :毛利最高
    public static void getaleAnalyprofit(String begin, String end, int page, int pageSize) {
        Api.getSalesAnalyTotal(Url.ANALY_SALE_GROSS_PROFIT, begin, end, page, pageSize, new BaseRequest.OnRequestListener() {
            @Override
            public void onSuccess(Result result) {
                result.seteCode(AnalysisActivity.SALE_ANALY_TOP_PROFIT);
                EventBus.getDefault().post(result);
            }

            @Override
            public void onError(String errorCode, String message) {
                EventBus.getDefault().post(message);
            }
        });
    }



  /*  //消费统计
    public static void getSaleAnalyTtotalTonsumption(String begin, String end, int page, int pageSize) {
        code = SALE_ANALY_TTOTAL_CONSUMPTION;
        Api.getSalesAnalyTotal(Url.ANALY_SALE_TTOTAL_CONSUMPTION,begin,end,page,pageSize, listener);
    }
    //销售总额
    public static void getSaleAnalyGrosssales(String begin, String end, int page, int pageSize) {
        code = SALE_ANALY_GROSSSALES;
        Api.getSalesAnalyTotal(Url.ANALY_SALE_GROSSSALES,begin,end,page,pageSize, listener);
    }
    //销售毛利润
    public static void getSaleAnalyGrossprofit(String begin, String end, int page, int pageSize) {
        code = SALE_ANALY_GROSS_PROFIT;
        Api.getSalesAnalyTotal(Url.ANALY_SALE_GROSS_PROFIT,begin,end,page,pageSize, listener);
    }

    //销售总量
    public static void getSaleAnalySalescount(String begin, String end, int page, int pageSize) {
        code = SALE_ANALY_SALESCOUNT;
        Api.getSalesAnalyTotal(Url.ANALY_SALE_SALESCOUNT,begin,end,page,pageSize, listener);
    }*/
}
