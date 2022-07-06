package com.xzy.pojo.user.code;

import com.google.common.collect.ImmutableMap;
import com.xzy.common.model.response.ResultCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

@ToString
public enum UcenterCode implements ResultCode {
    UCENTER_USERNAME_NONE(false, 23001, "请输入账号！"),
    UCENTER_PASSWORD_NONE(false, 23002, "请输入密码！"),
    UCENTER_VERIFYCODE_NONE(false, 23003, "请输入验证码！"),
    UCENTER_ACCOUNT_NOTEXISTS(false, 23004, "账号不存在！"),
    UCENTER_CREDENTIAL_ERROR(false, 23005, "账号或密码错误！"),
    UCENTER_LOGIN_ERROR(false, 23006, "登陆过程出现异常请尝试重新操作！"),
    UCENTER_USERNAME_EXIST(false, 23007, "账号已经存在！"),
    UCENTER_USERNAME_ERROR(false, 23008, "账号格式错误！"),
    UCENTER_PASSWORD_ERROR(false, 23009, "密码格式错误！"),
    UCENTER_NAME_ERROR(false, 23010, "姓名错误！"),
    UCENTER_DEPOSIT_NOT(false, 23011, "上级可用余额不足！"),
    UCENTER_WITHDRAWAL_NOT(false, 23012, "下级额度以使用,请回收下级额度！"),
    UCENTER_BALANCE_EXCEED(false, 23013, "超出余额！"),
    UCENTER_MARKET_ERROR(false, 23014, "调赔设置错误！"),
    UCENTER_PT_ERROR(false, 23015, "占成设置错误！"),
    UCENTER_LOWER_MIN_PT_EXCEED(false, 23016, "下线最低占成超出！"),
    UCENTER_LOWER_MAX_PT_EXCEED(false, 23017, "下线最高占成超出！"),
    UCENTER_LOWER_MIN_PT_EXCEED_MAX(false, 23018, "下线最低占成大于最高占成！"),
    UCENTER_BALANCE_LIMIT(false, 23019, "信用额度范围错误！"),
    UCENTER_OLDPASSWORD_ERROR(false, 23020, "旧密码输入错误！"),
    UCENTER_NO_PWD(false,23030, "新旧密码不能重复,请重新输入!"),
    UCENTER_SUPER_OMNIBUS_ERROR(false, 23021, "上级账号不是综合账户！"),
    UCENTER_MEMBER_NOT_UPDATE(false, 23022, "会员数据不能修改！"),
    UCENTER_LOGIN_PWD_ERROR(false, 23023, "登陆密码错误！"),
    UCENTER_SEC_PWD_ERROR(false, 23024, "二级密码错误！"),
    UCENTER_MAX_USER_ERROR(false, 23025, "可开账号已达上限！"),
    UCENTER_SECURITY_CODE_ERROR(false, 23026, "搜索码格式错误!"),
    UCENTER_SECURITY_CODE_EXIST(false, 23027, "搜索码重复!"),
    UCENTER_SITE_CODE_ERROR(false, 23028, "搜索码错误!"),
    UCENTER_USER_ERROR(false, 23029, "开账号失败!"),
    UCENTER_UNLOCK(false, 23031, "账号未锁定!"),
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

    private UcenterCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    private static final ImmutableMap<Integer, UcenterCode> CACHE;

    static {
        final ImmutableMap.Builder<Integer, UcenterCode> builder = ImmutableMap.builder();
        for (UcenterCode commonCode : values()) {
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
