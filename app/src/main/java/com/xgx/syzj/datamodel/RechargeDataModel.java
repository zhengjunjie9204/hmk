package com.xgx.syzj.datamodel;

import com.xgx.syzj.app.Api;
import com.xgx.syzj.base.BaseRequest;
import com.xgx.syzj.bean.Recharge;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.event.BaseDataEvent;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.widget.list.ListPageInfo;
import com.xgx.syzj.widget.list.PagedListDataModel;

import java.util.List;

/**
 * @author zajo
 * @created 2015年10月30日 11:01
 */
public class RechargeDataModel extends PagedListDataModel<Recharge> {

    public static final byte ADD_RECHARGE = 0x10;
    public static final byte LIST_RECHARGE = 0x11;
    public static final byte EXC_INTEGRAL = 0x12;
    public static final byte MEMBER_CARD_IFO = 0x12;

    private static byte code;
    private int mAssociatorId;

    private BaseDataEvent<Recharge> data = new BaseDataEvent<>();

    public RechargeDataModel(int num, int associatorId) {
        mListPageInfo = new ListPageInfo<>(num);
        mAssociatorId = associatorId;
    }

    @Override
    protected void doQueryData() {
        code = LIST_RECHARGE;
        Api.getRechargeList(mAssociatorId, new BaseRequest.OnRequestListener() {

            @Override
            public void onSuccess(Result result) {
                List<Recharge> list = FastJsonUtil.json2List(result.getResult(), Recharge.class);
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

    //用户储值
    public static void addRecharge(int associatorId, int rechargeAmount, int giftAmount, int rechargeCount, int rechargeType, int modeOfPayment, String description) {
        code = ADD_RECHARGE;
        Api.addRecharge(associatorId, rechargeAmount, giftAmount, rechargeCount, rechargeType, modeOfPayment, description, listener);
    }

    //兑换积分
    public static void exchangeIntegral(String cardId, int productId, int integral, String description){
        code = EXC_INTEGRAL;
        Api.exchangeIntegral(cardId, productId, integral, description, listener);
    }

    //获取会员卡详情
    public static void getMemberCardDetail(int cardId) {
        code = MEMBER_CARD_IFO;
        Api.getCardDetail(cardId, listener);
    }
}
