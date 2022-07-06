package com.xzy.pojo.user.code;

import com.google.common.collect.ImmutableMap;
import com.xzy.common.model.response.ResultCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;


@ToString
public enum AuthCode implements ResultCode {
    AUTH_LOGIN_SUCCESS(true,200,"登录成功！"),
    AUTH_USERNAME_NONE(false,23001,"请输入账号！"),
    AUTH_PASSWORD_NONE(false,23002,"请输入密码！"),
    AUTH_VERIFYCODE_NONE(false,23003,"请输入验证码！"),
    AUTH_ACCOUNT_NOTEXISTS(false,23004,"账号不存在！"),
    AUTH_CREDENTIAL_ERROR(false,23005,"账号或密码错误！"),
    AUTH_LOGIN_ERROR(false,23006,"登录失败！"),
    AUTH_LOGIN_APPLYTOKEN_FAIL(false,23007,"申请令牌失败！"),
    AUTH_LOGIN_TOKEN_SAVEFAIL(false,23008,"存储令牌失败！"),
    AUTH_LOGIN_TYPE_ERROR(false,23009,"登陆平台错误！"),
    AUTH_ACCOUNT_CLOSE(false,23010,"账号关闭！"),
    AUTH_USERIP_ERROR(false,23011,"登录IP错误！"),
    AUTH_CAPTCHA_ERROR(false,23012,"验证码错误！"),
    AUTH_CAPTCHA_GET(false,23013,"请先获取验证码！"),
    AUTH_PASSWORD_ERROR(false,23014,"密码错误次数过多，请联系上级解锁！"),
    AUTH_BOUND_IP(false,23015,"请使用绑定登陆IP！"),
    AUTH_PASSWORD_EXPIRE(false,23016,"密码14天已使用，为安全起见请更换密码！"),
    AUTH_PASSWORD_UPDATE(false,23017,"请修改登录密码！"),
    AUTH_PASSWORD_UPDATE_SG(false,23018,"请修改登录密码！"),
    AUTH_PASSWORD_UPDATE_IDC(false,23019,"请修改登录密码！"),
    ;

    //操作代码
    @ApiModelProperty(value = "操作是否成功", example = "true", required = true)
    boolean success;

    //操作代码
    @ApiModelProperty(value = "操作代码", example = "22001", required = true)
    int code;
    //提示信息
    @ApiModelProperty(value = "操作提示", example = "操作过于频繁！", required = true)
    String message;
    private AuthCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
    private static final ImmutableMap<Integer, AuthCode> CACHE;

    static {
        final ImmutableMap.Builder<Integer, AuthCode> builder = ImmutableMap.builder();
        for (AuthCode commonCode : values()) {
            builder.put(commonCode.code(), commonCode);
        }
        CACHE = builder.build();
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
