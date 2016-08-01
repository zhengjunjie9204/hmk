package com.xgx.syzj.datamodel;

import com.xgx.syzj.base.BaseRequest;
import com.xgx.syzj.bean.Attendance;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.event.AttendanceListDataEvent;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.widget.list.ListPageInfo;
import com.xgx.syzj.widget.list.PagedListDataModel;

import java.util.ArrayList;

/**
 * @author zajo
 * @created 2015年10月12日 15:00
 */
public class AttendanceListDataModel extends PagedListDataModel<Attendance> {

    private String param1,param2;
    private int count;
    AttendanceListDataEvent data = new AttendanceListDataEvent();

    public AttendanceListDataModel(String param1, String param2){
        this.param1 = param1;
        this.param2 = param2;
        mListPageInfo = new ListPageInfo<Attendance>(10);
    }

    @Override
    protected void doQueryData() {
        BaseRequest.OnRequestListener onRequestListener = new BaseRequest.OnRequestListener() {
            @Override
            public void onSuccess(Result result) {
                EventCenter.getInstance().post(result);
            }

            @Override
            public void onError(String errorCode, String message) {
                EventCenter.getInstance().post(errorCode);
            }
        };


        ArrayList<Attendance> list;
        if (mListPageInfo.getStart() == 0){
            //刷新
//            list = getNewData();
            count = 0;
        }
//        else {
            //加载更多
            list = getMoreData();
//        }
        if (count >= 10){
            data.hasMore = false;
        } else {
            data.hasMore = true;
        }
//        if (data.dataList == null) {
            data.dataList = list;
//        } else {
//            if (mListPageInfo.getStart() == 0){
//                data.dataList.addAll(0, list);
//            } else {
//                data.dataList.addAll(list);
//            }
//        }
//        try{
//            Thread.sleep(1500);
//        }catch (Exception e){}
        if (count == 5){
            setRequestFail();
            EventCenter.getInstance().post("加载失败，请重新请求。");
        } else {
            setRequestResult(data.dataList, data.hasMore);
            EventCenter.getInstance().post(data);
        }

    }

    private ArrayList<Attendance> getMoreData(){
        count++;
        ArrayList<Attendance> mList = new ArrayList<>();
        Attendance entry;
        for (int i = 0; i < 10; i++) {
            entry = new Attendance();
            entry.setId(i+"");
            entry.setUid(i + "");
            entry.setUserName("吴勇" + i);
            entry.setLate(i);
            entry.setAbsence(i);
            entry.setPhone("13"+i+"58"+i+"25684");
            mList.add(entry);
        }
        return mList;
    }

//    private ArrayList<Attendance> getNewData(){
//        ArrayList<Attendance> mList = new ArrayList<>();
//        Attendance entry;
//        for (int i = 0; i < 2; i++) {
//            entry = new Attendance();
//            entry.setId(i+"");
//            entry.setUid(i + "");
//            entry.setUserName("刷新" + i);
//            entry.setLate(i);
//            entry.setAbsence(i);
//            entry.setPhone("13"+i+"58"+i+"25684");
//            mList.add(entry);
//        }
//        return mList;
//    }
}
