package com.xzy.result.util;

import cn.hutool.core.util.ObjectUtil;
import com.xzy.result.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class ResultDisposeUtil {

    public static String disGameNo(String lotteryKey,String gameNo){
        if(ObjectUtil.equal(lotteryKey, Constants.lotteryName.XYNC.getKey())){
            return gameNo.substring(0,gameNo.length()-3)+""+gameNo.substring(9,gameNo.length());
        }
        /*if(ObjectUtil.equal(lotteryKey,Constants.lotteryName.JLK3.getKey())
                || ObjectUtil.equal(lotteryKey,Constants.lotteryName.JSK3.getKey())
                || ObjectUtil.equal(lotteryKey,Constants.lotteryName.GXK3.getKey())){
            return "20"+gameNo;
        }*/
        return gameNo;
    }
    public static String disResult(String lotteryKey,String result){
        //去除北京快乐8的赛果有01,09 --》 1,9
        /*if(ObjectUtil.equal(lotteryKey,Constants.lotteryName.LUCKY20.getKey())
                || ObjectUtil.equal(lotteryKey,Constants.lotteryName.BJFAST8.getKey())){
            String[] stringArr = result.split(",");
            int len = ObjectUtil.equal(lotteryKey,Constants.lotteryName.BJFAST8.getKey())?stringArr.length-1:stringArr.length;
            List<String> list = new ArrayList<>();
            for (int i = 0; i < len; i++) {
                Integer integer = Integer.parseInt(stringArr[i]);
                list.add(integer.toString());
            }
            return String.join(",",list);
        }
        if(ObjectUtil.equal(lotteryKey,Constants.lotteryName.JS6HC.getKey())){
            String[] stringArr = result.split(",");
            List<String> list = new ArrayList<>();
            for (int i = 0; i < stringArr.length; i++) {
                String num = stringArr[i].length()<=1?"0"+stringArr[i]:stringArr[i];
                list.add(num);
            }
            return String.join(",",list);
        }*/
        if(ObjectUtil.equal(lotteryKey,Constants.lotteryName.CQSSC.getKey())||ObjectUtil.equal(lotteryKey,Constants.lotteryName.JSSSC.getKey())){
            String[] stringArr = result.split(",");
            List<String> list = new ArrayList<>();
            for (int i = 0; i < stringArr.length; i++) {
                Integer integer = Integer.parseInt(stringArr[i]);
                list.add(integer.toString());
            }
            return String.join(",",list);
        }
        return result;
    }

    /**
     * 添加赛果无 0
     * @param lotteryKey
     * @param result
     * @return
     */
    public static String disResultAddZero(String lotteryKey,String result){
        //添加北京赛车0
        if(ObjectUtil.equal(lotteryKey,Constants.lotteryName.BJPK10.getKey())
                ||ObjectUtil.equal(lotteryKey,Constants.lotteryName.LUCKY10.getKey())
                ||ObjectUtil.equal(lotteryKey,Constants.lotteryName.SPEED10.getKey())
                ||ObjectUtil.equal(lotteryKey,Constants.lotteryName.XYFT.getKey())
                ||ObjectUtil.equal(lotteryKey,Constants.lotteryName.GDKL10.getKey())){
            String[] stringArr = result.split(",");
            int len = stringArr.length;
            List<String> list = new ArrayList<>();
            for (int i = 0; i < len; i++) {
                String num = stringArr[i].length()<=1?"0"+stringArr[i]:stringArr[i];
                list.add(num);
            }
            return String.join(",",list);
        }
        return result;
    }

}
