package com.xzy.pojo.tio.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 消息类型公告类
 */
public class CodeUtil {
    public static final String MSG_PING = "ping";
    public static final String MSG_ERROR = "error";
    public static final String MSG_NOTICE = "notice";
    public static final String MSG_RESULT = "result";

    public static final String MSG_ODDS_INIT= "odds_init";
    public static final String MSG_ODDS_MARKET = "odds_market";
    public static final String MSG_ODDS_LOTTERY = "odds_lottery";
    public static final String MSG_ODDS_UNLOTTERY = "odds_unlottery";


    public static String getHost(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
    }
}
