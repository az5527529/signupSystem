package com.exception;

/**
 * Created by victor on 2018/3/8.
 */
public class MessageException extends Exception {
    private String errorMsg;
    private String errorCode;

    public MessageException(){}

    public MessageException(String errorMsg){
        this.errorMsg = errorMsg;
    }
    public MessageException(String errorMsg,String errorCode){
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public MessageException setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public MessageException setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }
}
