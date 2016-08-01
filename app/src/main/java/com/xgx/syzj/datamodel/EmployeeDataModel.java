package com.xgx.syzj.datamodel;

import com.xgx.syzj.app.Api;
import com.xgx.syzj.base.BaseRequest;
import com.xgx.syzj.bean.Employeebean;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.event.BaseDataEvent;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.widget.list.ListPageInfo;
import com.xgx.syzj.widget.list.PagedListDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sam on 2016/3/9 13:47.
 */
public class EmployeeDataModel extends PagedListDataModel<Employeebean>{

    public static final byte ADD_EMPLOYEE = 0x10;
    public static final byte LIST_EMPLOYEE = 0x11;
    public static final byte DELETE_EMPLOYEE = 0x12;
    public static final byte UPDATE_EMPLOYEE = 0x13;
    public static final byte COUNT_EMPLOYEE = 0x14;

    private  static  byte code;
    private String key;
    private BaseDataEvent<Employeebean> data = new BaseDataEvent<>();

    public EmployeeDataModel(int num) {
        mListPageInfo = new ListPageInfo<>(num);
    }

    public void  setKey(String key){

        this.key = key;
        data.dataList = null;
        data.hasMore = false;
        mListPageInfo = new ListPageInfo<>(mListPageInfo.getNumPerPage());
    }

    @Override
    protected void doQueryData() {
        Api.getEmployeeList(key,mListPageInfo.getPage(), mListPageInfo.getNumPerPage(), new BaseRequest.OnRequestListener() {
            @Override
            public void onSuccess(Result result) {
                List<Employeebean> list = FastJsonUtil.json2List(result.getResult(),Employeebean.class);
                data.dataList = list;
                if(list != null && list.size() > 0){
                    if(list.size() > mListPageInfo.getNumPerPage()){
                        data.hasMore = true;
                    }else {
                        data.hasMore = false;
                    }
                }else {
                    data.hasMore = false;
                    list = new ArrayList<>();
                }
                setRequestResult(data.dataList,data.hasMore);
                EventCenter.getInstance().post(list);
            }

            @Override
            public void onError(String errorCode, String message) {
                setRequestFail();
                EventCenter.getInstance().post(message);
            }
        });
    }


    private static BaseRequest.OnRequestListener listener = new BaseRequest.OnRequestListener() {

        @Override
        public void onSuccess(Result result) {
            result.seteCode(code);
            EventCenter.getInstance().post(result);
        }

        @Override
        public void onError(String errorCode, String message) {
            EventCenter.getInstance().post(message);
        }
    };

    /**
     * 删除员工
     * @param index
     */
    public static void deleteEmployee(String index){
        code = DELETE_EMPLOYEE;
        Api.deleteEmployee(index,listener);
    }


    /**
     * 添加员工
     * @param phoneStr
     * @param pswStr
     * @param nameStr
     * @param authority
     * @param storeId
     */
    public static void addEmployee(String phoneStr, String pswStr, String nameStr, int authority, int storeId){
        code = ADD_EMPLOYEE;
        Api.registerStaff(phoneStr, pswStr, nameStr, authority, storeId, listener);
    }


    /**
     * 修改员工
     * @param accountId
     * @param mobilePhone
     * @param password
     * @param authority
     */
    public static void updateEmployee(String accountId, String mobilePhone, String password, int authority){
        code = UPDATE_EMPLOYEE;
        Api.updateEmployee(accountId, mobilePhone, password, authority,listener);
    }

    /**
     *
     * @param key
     */
    public static void getEmployeeforKey(String key){
        code = COUNT_EMPLOYEE;
    }
}
