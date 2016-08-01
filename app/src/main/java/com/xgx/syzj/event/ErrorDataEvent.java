package com.xgx.syzj.event;

/**
 * @author zajo
 * @created 2015年10月14日 09:45
 */
public class ErrorDataEvent {

    String errorCode;
    String message;

    public ErrorDataEvent(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
