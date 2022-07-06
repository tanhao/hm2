package com.xzy.auth.utils;

import cn.hutool.core.util.IdUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BCryptUtil {
    /**
     * 加密字符串
     *
     * @param password
     * @return
     */
    public static String encode(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashPass = passwordEncoder.encode(password);
        return hashPass;
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


    public static String uuid() {
        return IdUtil.fastSimpleUUID();
    }

    public static String shortUuid() {
        return IdUtil.fastSimpleUUID().substring(0, 6);
    }
}