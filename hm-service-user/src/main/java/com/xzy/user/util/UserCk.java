package com.xzy.user.util;

import cn.hutool.core.util.StrUtil;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.pojo.user.code.UcenterCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


public class UserCk {
    /**
     * 验证登陆账号
     *
     * @param username
     * @return
     */
    public static String checkUserName(String username) {
        ExceptionCast.castCheck(StrUtil.isBlank(username), UcenterCode.UCENTER_USERNAME_NONE);
        username = username.toLowerCase();
        Boolean isMatcher = username.matches("^[a-zA-Z0-9@]{2,10}$");
        ExceptionCast.castCheck(!isMatcher, UcenterCode.UCENTER_USERNAME_ERROR);
        return username;
    }

    /***
     * 验证登陆密码
     * @param password
     * @return
     */
    public static String checkPassword(String password) {
        ExceptionCast.castCheck(StrUtil.isBlank(password), UcenterCode.UCENTER_PASSWORD_NONE);
        Boolean isMatcher = password.matches("^[a-zA-Z]{2}[a-zA-Z0-9@]{6,16}");
        ExceptionCast.castCheck(!isMatcher, UcenterCode.UCENTER_PASSWORD_ERROR);
        password = BCryptUtil.encode(password);
        return password;
    }

    /**
     * 验证搜索码
     *
     * @param code
     * @return
     */
    public static String checkSecurityCode(String code) {
        ExceptionCast.castCheck(StrUtil.isBlank(code), UcenterCode.UCENTER_SECURITY_CODE_ERROR);
        code = code.toLowerCase();
        Boolean isMatcher = code.matches("^[0-9]{5}$");
        ExceptionCast.castCheck(!isMatcher, UcenterCode.UCENTER_SECURITY_CODE_ERROR);
        return code;
    }

    /**
     * 验证全名
     *
     * @param fullName
     * @return
     */
    public static String checkFullName(String fullName) {
        if (StrUtil.isNotBlank(fullName)) {
            Boolean isMatcher = Boolean.FALSE;
            if (fullName.length() < 2 || fullName.length() > 8) {
                isMatcher = Boolean.TRUE;
            }
            ExceptionCast.castCheck(isMatcher, UcenterCode.UCENTER_NAME_ERROR);
        }
        return fullName;
    }
    /**
     * 判断密码是否正确
     *
     * @param password 未加密字符串
     * @param hashPass 加密字符串
     * @return
     */
    public static boolean matches(String password, String hashPass) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean f = passwordEncoder.matches(password, hashPass);
        return f;
    }

}
