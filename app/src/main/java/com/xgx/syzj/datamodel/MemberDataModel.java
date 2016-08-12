package com.xgx.syzj.datamodel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xgx.syzj.app.Api;
import com.xgx.syzj.base.BaseRequest;
import com.xgx.syzj.bean.Member;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.event.BaseDataEvent;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.widget.list.ListPageInfo;
import com.xgx.syzj.widget.list.PagedListDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zajo
 * @created 2015年10月26日 16:44
 */
public class MemberDataModel extends PagedListDataModel<Member> {

    public static final byte ADD_MEMBER = 0x10;
    public static final byte LIST_MEMBER = 0x11;
    public static final byte DELETE_MEMBER = 0x12;
    public static final byte UPDATE_MEMBER = 0x13;
    public static final byte COUNT_MEMBER = 0x14;
    public static final byte BASE_INFO = 0x16;

    private static byte code;
    private String key;
    private BaseDataEvent<Member> data = new BaseDataEvent<>();

    public MemberDataModel(int num) {
        mListPageInfo = new ListPageInfo<>(num);
    }

    public void setKey(String key) {
        this.key = key;
        data.dataList = null;
        data.hasMore = false;
        mListPageInfo = new ListPageInfo<>(mListPageInfo.getNumPerPage());
    }

    @Override
    protected void doQueryData() {
        code = LIST_MEMBER;
        Api.getAssociatorList(key, mListPageInfo.getPage(), mListPageInfo.getNumPerPage(), new BaseRequest.OnRequestListener() {

            @Override
            public void onSuccess(Result result) {
                JSONObject object= JSON.parseObject(result.getResult());
                List<Member> list;
                if(result.getStatus()==200) {
                    list = FastJsonUtil.json2List(object.getString("members"), Member.class);
                }else {
                    list=new ArrayList<>();
                }
                data.dataList = list;
                if (list != null && list.size() > 0) {
                    if (list.size() >= mListPageInfo.getNumPerPage()) {
                        data.hasMore = true;
                    } else {
                        data.hasMore = false;
                    }
                } else {
                    data.hasMore = false;
                }
                setRequestResult(data.dataList, data.hasMore);
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

    //添加会员
    public static void addMember(String name, String carNumber, String telephone, String carType, String cardNumber) {
        code = ADD_MEMBER;
        Api.addAssociator(name, carNumber, telephone, carType, cardNumber, listener);
    }

    //删除会员
    public static void deleteMember(int id) {
        code = DELETE_MEMBER;
        Api.deleteAssociator(id, listener);
    }

    //更新会员信息
    public static void updateMember(long id, String name, String carNumber, String telephone, String carType, String cardNumber) {
        code = UPDATE_MEMBER;
        Api.updateAssociator(id, name, carNumber, telephone, carType, cardNumber, listener);
    }

    //获取基础信息
    public static void getMemberBaseInfo(int memberId) {
        code = BASE_INFO;
        Api.getMemberBaseInfo(memberId, listener);
    }

    //获取会员总数量
    public static void getMemberCount() {
        code = COUNT_MEMBER;
        Api.getAssociatorCount(listener);
    }
}