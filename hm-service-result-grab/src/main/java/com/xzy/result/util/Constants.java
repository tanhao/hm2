package com.xzy.result.util;

import cn.hutool.core.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Constants {

    public enum lotteryName {
        //赛车组
        BJPK10(1200, "bjpk10"),//北京赛车pk10
        LUCKY10(300, "lucky10"),//澳洲幸运10
        SBSPEED10(75, "sbspeed10"),//极速飞艇
        SPEED10(75, "speed10"),//极速赛车
        XYFT(300, "xyft"),//幸运飞艇
        HMPK10(300, "hmpk10"),//老北京赛车

        //时时彩组
        CQSSC(1200, "cqssc"),//重庆时时彩
        JSSSC(75, "jsssc"),//极速时时彩
        LUCKY5(300, "lucky5"),//澳洲幸运5
        XJSSC(1200, "xjssc"),//新疆时时彩
        HMCQSSC(300, "hmcqssc"),//老重庆时时彩

        //快乐十分
        XYNC(1200, "xync"),//幸运农场
        SPEED8(75, "speed8"),//极速快乐十分
        GDKL10(1200, "gdkl10"),//广东快乐十分
        LUCKY8(300, "lucky8"),//澳洲幸运8

        //pcdd
        JND28(210, "jnd28"),//加拿大28
        PCDD(300, "pcdd"),//PC蛋蛋 拿澳洲幸运20计算
        JSDD(75, "jsdd"),//极速蛋蛋 拿极速快乐8计算

        LUCKY20(300, "lucky20"),//澳洲幸运20
        JSKL8(75, "jskl8"),//极速快乐8
        KENO(210, "keno"),//keno



        ;
        private Integer expTime;
        private String key;

        public Integer getExpTime() {
            return expTime;
        }

        public String getKey() {
            return key;
        }

        lotteryName(int expTime, String key) {
            this.expTime = expTime;
            this.key = key;
        }

    }

    public static Integer getValueByExpTime(String key) {
        for (lotteryName s : Constants.lotteryName.values()) {
            if (s.getKey().equals(key)) {
                if (s.expTime > 300) {
                    return 300;
                }
                return s.expTime;
            }
        }
        return 0;
    }

    public static String kai_168 = "kai_168"; //开奖168
    public static Integer kai_168_time = 3000; //开奖168

    public static String kai_163 = "kai_163"; //1396网
    public static String hm_zkc = "hm_zkc"; //zkc
    public static String kai_bclc = "kai_bclc"; //加拿大

    //验证结果是否正确
    public static String lotteryCodeNum(String lotteryStr) {
        String[] str = lotteryStr.split(":");
        String lotteryKey = str[0];
        String[] strings = str[1].split(",");

        Boolean bool = false;
        String[] codeNums = null;
        List<String> codeResult = new ArrayList<>();
        try {
            if (ObjectUtil.equal(lotteryKey, lotteryName.BJPK10.getKey()) || ObjectUtil.equal(lotteryKey, lotteryName.XYFT.getKey()) ||
                    ObjectUtil.equal(lotteryKey, lotteryName.SPEED10.getKey()) || ObjectUtil.equal(lotteryKey, lotteryName.LUCKY10.getKey()) ||
                    ObjectUtil.equal(lotteryKey, lotteryName.SBSPEED10.getKey())) {
                if (strings.length == 10) {
                    codeNums = "01,02,03,04,05,06,07,08,09,10".split(",");
                    for (String number : strings) {
                        String num = Integer.parseInt(number) == 10 ? Integer.parseInt(number) * 1 + "" : "0" + Integer.parseInt(number) * 1;
                        codeResult.add(num);
                    }
                }
            }
            if (ObjectUtil.equal(lotteryKey, lotteryName.GDKL10.getKey()) || ObjectUtil.equal(lotteryKey, lotteryName.SPEED8.getKey()) ||
                    ObjectUtil.equal(lotteryKey, lotteryName.XYNC.getKey()) || ObjectUtil.equal(lotteryKey, lotteryName.LUCKY8.getKey())) {
                if (strings.length == 8) {
                    codeNums = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20".split(",");
                    for (String number : strings) {
                        codeResult.add((Integer.parseInt(number) * 1) + "");
                    }
                }
            }
            if (ObjectUtil.equal(lotteryKey, lotteryName.CQSSC.getKey()) || ObjectUtil.equal(lotteryKey, lotteryName.JSSSC.getKey()) ||
                    ObjectUtil.equal(lotteryKey, lotteryName.LUCKY5.getKey()) || ObjectUtil.equal(lotteryKey, lotteryName.XJSSC.getKey())) {
                if (strings.length == 5) {
                    codeNums = "0,1,2,3,4,5,6,7,8,9".split(",");
                    for (String number : strings) {
                        codeResult.add((Integer.parseInt(number) * 1) + "");
                    }
                }
            }
            if (ObjectUtil.equal(lotteryKey, lotteryName.JSDD.getKey()) || ObjectUtil.equal(lotteryKey, lotteryName.PCDD.getKey())
                    || ObjectUtil.equal(lotteryKey, lotteryName.JND28.getKey())) {
                if (strings.length == 3) {
                    codeNums = "0,1,2,3,4,5,6,7,8,9".split(",");
                    for (String number : strings) {
                        codeResult.add((Integer.parseInt(number) * 1) + "");
                    }
                }
            }
            if (ObjectUtil.equal(lotteryKey, lotteryName.LUCKY20.getKey())
                    ||ObjectUtil.equal(lotteryKey, lotteryName.JSKL8.getKey())
                    ||ObjectUtil.equal(lotteryKey, lotteryName.KENO.getKey())) {
                if (strings.length >= 20) {
                    List<String> num= new ArrayList<>();
                    for (int i = 0; i < 80; i++) {
                        num.add(String.format("%02d",(i+1)));
                    }
                    codeNums= num.toArray(new String[num.size()]);
                    for (String number : strings) {
                        codeResult.add(String.format("%02d",Integer.parseInt(number)));
                    }
                }
            }
            bool = true;
            for (String s : codeResult) {
                if (!Arrays.asList(codeNums).contains(s)) {
                    bool = false;
                    break;
                }
            }
        } catch (Exception e) {
            bool = false;
            e.printStackTrace();
        } finally {
            if (bool) {
                return String.join(",", codeResult);
            }
            return "";
        }
    }

    final static Logger log = LoggerFactory.getLogger("Constants");

    public static Map<Boolean, Integer> errorMapExt(Map<Boolean, Integer> map, String https, String lotteryName, String ipKey) {
        Boolean b = map.keySet().iterator().next();
        Integer i = map.get(b);
        i++;
        if (i >= 15) {
            String redisKey = ipKey + ":" + https + ":" + lotteryName;
            b = false;
            log.info("-------------------------------------------[hm 2 采集网：" + https + " 异常：" + lotteryName + "] 已停止-----------------------------------------------------");
//			RedisUtil.getAndSetValue(redisKey,4);
            ///String msg = SendFeige.sendUser(lotteryName);
        } else {
            b = true;
        }
        Map<Boolean, Integer> retMap = new HashMap<>();
        retMap.put(b, i);
        return retMap;
    }

    ;

    public static Map<Boolean, Integer> initMapExt() {
        Map<Boolean, Integer> retMap = new HashMap<>();
        retMap.put(true, 0);
        return retMap;
    }

    ;


    public static Boolean isRunThreadTime(String lottery) {
        if (ObjectUtil.equal(lottery, lotteryName.BJPK10.getKey())) {
            Long nowDate = new Date().getTime();
            Long startDate = DateUtil.parase(DateUtil.format(new Date(), DateUtil.DATEFORMATDAY) + " 09:00:00", DateUtil.DATEFORMATSECOND).getTime();
            Long endDate = DateUtil.parase(DateUtil.format(new Date(), DateUtil.DATEFORMATDAY) + " 24:30:00", DateUtil.DATEFORMATSECOND).getTime();
            if (nowDate < startDate && nowDate > endDate) {
                return false;
            }
        }
        if (ObjectUtil.equal(lottery, lotteryName.GDKL10.getKey())) {
            Long nowDate = new Date().getTime();
            Long startDate = DateUtil.parase(DateUtil.format(new Date(), DateUtil.DATEFORMATDAY) + " 09:00:00", DateUtil.DATEFORMATSECOND).getTime();
            Long endDate = DateUtil.parase(DateUtil.format(new Date(), DateUtil.DATEFORMATDAY) + " 23:30:00", DateUtil.DATEFORMATSECOND).getTime();
            if (nowDate < startDate && nowDate > endDate) {
                return false;
            }
        }
        return true;
    }


}
