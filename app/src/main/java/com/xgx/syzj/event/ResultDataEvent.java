package com.xgx.syzj.event;

import com.xgx.syzj.bean.Result;

/**
 * @author zajo
 * @created 2015年10月14日 09:47
 */
public class ResultDataEvent {

    private byte code;

    private Result result;

    public ResultDataEvent(byte code, Result result) {
        this.code = code;
        this.result = result;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
