package com.xgx.syzj.datamodel;

import com.xgx.syzj.base.BaseRequest;
import com.xgx.syzj.bean.Result;
import com.xgx.syzj.event.EventCenter;

/**
 * @author zajo
 * @created 2015年10月14日 10:36
 */
public abstract class BaseDataModel {

    protected static byte code;

    public static BaseRequest.OnRequestListener listener = new BaseRequest.OnRequestListener(){

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
}
