package com.xgx.syzj.datamodel;

import com.xgx.syzj.app.Api;
import com.xgx.syzj.base.BaseRequest;
import com.xgx.syzj.bean.BillDetail;
import com.xgx.syzj.bean.Cart;
import com.xgx.syzj.bean.GoodsCart;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.event.BaseDataEvent;
import com.xgx.syzj.event.EventCenter;
import com.xgx.syzj.utils.FastJsonUtil;
import com.xgx.syzj.widget.list.ListPageInfo;
import com.xgx.syzj.widget.list.PagedListDataModel;

import java.util.List;

/**
 * 挂单
 *
 * @author zajo
 * @created 2015年11月25日 15:32
 */
public class CartDataModel extends PagedListDataModel<Cart> {

    public static final byte LOAD_CART_LIST = 0x0;//列表
    public static final byte CART_PUT_UP = 0x1;//挂单
    public static final byte CART_DETAIL = 0x2;//挂单详情
    public static final byte CART_BILL_CHECK = 0x3;//结算

    private static byte code;
    private BaseDataEvent<Cart> data = new BaseDataEvent<>();

    public CartDataModel(int num) {
        mListPageInfo = new ListPageInfo<>(num);
    }

    @Override
    protected void doQueryData() {
        code = LOAD_CART_LIST;
        Api.getExtCartList(mListPageInfo.getPage(), mListPageInfo.getNumPerPage(), new BaseRequest.OnRequestListener() {
            @Override
            public void onSuccess(Result result) {
                List<Cart> list = FastJsonUtil.json2List(result.getResult(), Cart.class);
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

    //挂单
    public static void cartPutUp(String cartName, int associatorId, double totalValue, String accountId, String description, List<GoodsCart> gcList){
        code = CART_PUT_UP;
        Api.cartPutUp(cartName, associatorId, totalValue, accountId, description, gcList, listener);
    }

    //详情
    public static void cartDetail(int cartId) {
        code = CART_DETAIL;
        Api.getCartDetails(cartId, listener);
    }

    //结算
    public static void cartBillCheck(int cartId, int associatorId, double receivableValue, double paidValue, int modeOfPay,int customerType ,List<BillDetail> billDetails) {
        code = CART_BILL_CHECK;
        Api.cartBillCheck(cartId, associatorId, receivableValue, paidValue, modeOfPay, customerType,billDetails, listener);
    }
}
