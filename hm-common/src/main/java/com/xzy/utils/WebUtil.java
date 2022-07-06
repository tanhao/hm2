package com.xzy.utils;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebUtil {


    //GET字符集设置处理
    public static Map<String, String> convertCharsetToUTF8(Map<String, String> searchMap) throws Exception {
        Iterator<Map.Entry<String, String>> entries = searchMap.entrySet().iterator();
        Map map = new HashMap();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            map.put(new String(entry.getKey().getBytes("ISO8859-1"), "UTF-8"), new String(entry.getValue().getBytes("ISO8859-1"), "UTF-8"));
        }
        return map;
    }

    public static String getCityByIP(String ip) throws JSONException {
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            return "本地";
        }
        try {
            URL url = new URL("http://opendata.baidu.com/api.php?query=" + ip + "&co=&resource_id=6006&t=1433920989928&ie=utf8&oe=utf-8&format=json");
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line = null;
            StringBuffer result = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            JSONObject jsStr = JSONObject.parseObject(result.toString());
            JSONArray jsData = (JSONArray) jsStr.get("data");
            JSONObject data = (JSONObject) jsData.get(0);//位置
            return (String) data.get("location");
        } catch (IOException e) {
            return "读取失败";
        }
    }


    //浏览器类型
    public static String getBrowserName(String agent) {
        agent = agent.toLowerCase();
        if (agent.indexOf("msie 7") > 0) {
            return "ie7";
        } else if (agent.indexOf("msie 8") > 0) {
            return "ie8";
        } else if (agent.indexOf("msie 9") > 0) {
            return "ie9";
        } else if (agent.indexOf("msie 10") > 0) {
            return "ie10";
        } else if (agent.indexOf("msie") > 0) {
            return "ie";
        } else if (agent.indexOf("opera") > 0) {
            return "opera";
        } else if (agent.indexOf("chrome") > 0) {
            return "chrome";
        } else if (agent.indexOf("firefox") > 0) {
            return "firefox";
        } else if (agent.indexOf("gecko") > 0 && agent.indexOf("rv:11") > 0) {
            return "ie11";
        } else if (agent.indexOf("iphone") > 0) {
            return "iPhone";
        } else if (agent.indexOf("ipad") > 0) {
            return "iPad";
        } else if (agent.indexOf("android") > 0) {
            return "Android";
        } else {
            return "others";
        }
    }

    public static String getRequestUserAgent(UserAgent ua) {
        Map map = new HashMap();
        try {
            //UserAgent ua = UserAgentUtil.parse(request.getHeader("User-Agent"));
            ua.getBrowser().toString();//Chrome
            ua.getEngine().toString();//Webkit
            ua.getEngineVersion();//535.1
            ua.getVersion();//14.0.835.163
            ua.getOs().toString();//Windows 7
            ua.getPlatform().toString();//Windows
            map.put("browser", ua.getBrowser().toString());
            map.put("os", ua.getOs().toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return JSONObject.toJSONString(map);//ua.getBrowser().toString()+" "+ua.getOs().toString();;
        }
    }

    /***
     * 获取网关传递的请求地址
     * @return
     */
    public static String getRealServerName(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String PATTERN_IP = "(\\d*\\.){3}\\d*";
        String url = request.getHeader("realServerName");
        /* 以IP形式访问时，返回IP */
        Pattern ipPattern = Pattern.compile(PATTERN_IP);
        Matcher matcher = ipPattern.matcher(url);
        if (matcher.find()) {
            return matcher.group();
        }
        if(url.indexOf("//")>-1) {
            url = url.substring(url.indexOf("//") + 2);
        }
        /* 以域名访问时，返回二级域名 */
//        url = url.substring(url.indexOf(".")+1);
        return url;
    }
}
