package com.xzy.common.exception;


import com.xzy.common.model.response.ResultCode;

/**
 * 手动抛出异常
 **/
public class ExceptionCast {

    //抛出枚举
    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }

    //判断是否，抛出枚举
    public static void castCheck(Boolean check,ResultCode resultCode){
        if(check){
            throw new CustomException(resultCode);
        }
    }

    public static void cast(String message){
        ResultCode resultCode = new ResultCode() {
            @Override
            public boolean success() {
                return false;
            }
            @Override
            public int code() {
                return 888888;
            }
            @Override
            public String message() {
                return message;
            }
        };
        throw new CustomException(resultCode);
    }

    /**
     * 附加data數據
     * @param resultCode
     * @param data
     */
    public static void castData(ResultCode resultCode,Object data){
        throw new CustomException(resultCode,data);
    }

}
