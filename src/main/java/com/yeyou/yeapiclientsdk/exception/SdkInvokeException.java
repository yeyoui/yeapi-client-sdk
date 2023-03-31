package com.yeyou.yeapiclientsdk.exception;

public class SdkInvokeException extends Exception{
    private final String msg;

    public SdkInvokeException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
