package com.xzy.common.model.response;

import com.xzy.common.model.code.CommonCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回json格式封装
 */
@Data
@NoArgsConstructor
public class ResponseResult implements Response {

    //操作是否成功
    boolean success = SUCCESS;

    //操作代码
    int code = SUCCESS_CODE;
    long serverTime=System.currentTimeMillis();
    //提示信息
    String message;

    public ResponseResult(ResultCode resultCode){
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public static ResponseResult SUCCESS(){
        return new ResponseResult(CommonCode.SUCCESS);
    }
    public static ResponseResult FAIL(){
        return new ResponseResult(CommonCode.FAIL);
    }
    public static ResponseResult ERROR(ResultCode resultCode){
        return new ResponseResult(resultCode);
    }

    public static ResponseResult INIT(ResultCode resultCode){
        return new ResponseResult(resultCode);
    }
}
