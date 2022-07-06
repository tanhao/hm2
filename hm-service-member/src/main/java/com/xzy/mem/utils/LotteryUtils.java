package com.xzy.mem.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName LotteryUtils
 * @Auther yzy
 * @Date 2019/6/25 9:37
 * @Version 1.0
 */
public class LotteryUtils {

    /**
     * 彩票组
     *@auther yzy
     *@date 2019/8/12 17:08
     */
    public final static String GROUP_PK10="carship";
    public final static String GROUP_SSC="ssc";
    public final static String GROUP_HAPPY10="gdkl10";
    public final static String GROUP_FAST3="fast3";
    public final static String GROUP_11X5="11x5";
    public final static String GROUP_LUCK28="luck28";
    public final static String GROUP_KL8="fast8";
    public final static String GROUP_GXKL10="gxklsf";
    public final static String GROUP_6HC="6hc";

    public static final Map<String,Integer[]> OVER_UNDER_VALUE_MAP = new HashMap<>();
    static{
        OVER_UNDER_VALUE_MAP.put(GROUP_PK10,new Integer[]{11,5});
        OVER_UNDER_VALUE_MAP.put(GROUP_SSC,new Integer[]{22,4});
        OVER_UNDER_VALUE_MAP.put(GROUP_HAPPY10,new Integer[]{84,10});
        OVER_UNDER_VALUE_MAP.put(GROUP_FAST3,new Integer[]{10,0});
        OVER_UNDER_VALUE_MAP.put(GROUP_KL8,new Integer[]{809,0});
        OVER_UNDER_VALUE_MAP.put(GROUP_LUCK28,new Integer[]{13,13});
        OVER_UNDER_VALUE_MAP.put(GROUP_11X5,new Integer[]{29,5});
        OVER_UNDER_VALUE_MAP.put(GROUP_GXKL10,new Integer[]{55,10});//55 合大 10大
        OVER_UNDER_VALUE_MAP.put(GROUP_6HC,new Integer[]{175,24});//55 合大 10大
    }

    /**
     * 各种路纸信息
     *@auther yzy
     *@date 2019/8/12 17:08
     */
    public final static String OVER="over";
    public final static String UNDER="under";
    public final static String DRAW="draw";
    public final static String ODD="odd";
    public final static String EVEN="even";
    public final static String DRAGON="dragon";//龙
    public final static String TIGER="tiger";//虎
    public final static String RED="redBall";//红波
    public final static String GREEN="greenBall";//绿波
    public final static String BLUE="blueBall";//蓝波
    public final static String GOLD="gold";//金
    public final static String WOOD="wood";//木
    public final static String WATER="water";//水
    public final static String FIRE="fire";//火
    public final static String EARTH="earth";//土
    public final static String TOP="top";//前
    public final static String END="end";//后
    public final static String F="f";//福
    public final static String L="l";//禄
    public final static String S="s";//寿
    public final static String X="x";//喜


    /**
     *  需要验证betcontent的赔率数据
     *@auther yzy
     *@date 2019/9/20 19:24
     */
    public final static Map<String,Map<String,Map<String,Integer>>> BET_CONTENT_NUM_MAP=new HashMap<>();
    static{
        Map<String,Map<String,Integer>> playMap=new HashMap<>();
        Map<String,Integer> oddsMap=new HashMap<>();
        oddsMap.put("x2",2);
        oddsMap.put("x2lz",2);
        oddsMap.put("x2lg",2);
        oddsMap.put("x3",3);
        oddsMap.put("x3topz",3);
        oddsMap.put("x3topg",3);
        oddsMap.put("x4",4);
        oddsMap.put("x5",5);
        playMap.put("lianma",oddsMap) ;
        BET_CONTENT_NUM_MAP.put(GROUP_HAPPY10,playMap);

        playMap=new HashMap<>();
        oddsMap=new HashMap<>();
        oddsMap.put("wq",2);
        oddsMap.put("wb",2);
        oddsMap.put("ws",2);
        oddsMap.put("wg",2);
        oddsMap.put("qb",2);
        oddsMap.put("qs",2);
        oddsMap.put("qg",2);
        oddsMap.put("bs",2);
        oddsMap.put("bg",2);
        oddsMap.put("sg",2);
        playMap.put("2dw",oddsMap) ;
        oddsMap=new HashMap<>();
        oddsMap.put("top3",3);
        oddsMap.put("mid3",3);
        oddsMap.put("end3",3);
        playMap.put("3dw",oddsMap) ;
        oddsMap=new HashMap<>();
        oddsMap.put("5",5);
        oddsMap.put("6",6);
        oddsMap.put("7",7);
        oddsMap.put("8",8);
        oddsMap.put("9",9);
        oddsMap.put("10",10);
        playMap.put("zx3top3",oddsMap) ;
        playMap.put("zx3mid3",oddsMap) ;
        playMap.put("zx3end3",oddsMap) ;
        oddsMap=new HashMap<>();
        oddsMap.put("4",4);
        oddsMap.put("5",5);
        oddsMap.put("6",6);
        oddsMap.put("7",7);
        oddsMap.put("8",8);
        playMap.put("zx6top3",oddsMap) ;
        playMap.put("zx6mid3",oddsMap) ;
        playMap.put("zx6end3",oddsMap) ;
        oddsMap=new HashMap<>();
        oddsMap.put("top3",3);
        oddsMap.put("mid3",3);
        oddsMap.put("end3",3);
        playMap.put("fs",oddsMap) ;
        BET_CONTENT_NUM_MAP.put(GROUP_SSC,playMap);

        playMap=new HashMap<>();
        oddsMap=new HashMap<>();
        oddsMap.put("ZXQ2",2);
        playMap.put("q2zx",oddsMap) ;
        oddsMap=new HashMap<>();
        oddsMap.put("ZXQ3",3);
        playMap.put("q3zx",oddsMap) ;
        oddsMap=new HashMap<>();
        oddsMap.put("Q2GX",2);
        playMap.put("q2gx",oddsMap) ;
        oddsMap=new HashMap<>();
        oddsMap.put("Q3GX",3);
        playMap.put("q3gx",oddsMap) ;
        oddsMap=new HashMap<>();
        oddsMap.put("Q2ZX",2);
        playMap.put("rx2",oddsMap) ;
        oddsMap=new HashMap<>();
        oddsMap.put("Q3ZX",3);
        playMap.put("rx3",oddsMap) ;
        oddsMap=new HashMap<>();
        oddsMap.put("Q4ZX",4);
        playMap.put("rx4",oddsMap) ;
        oddsMap=new HashMap<>();
        oddsMap.put("Q5ZX",5);
        playMap.put("rx5",oddsMap) ;
        oddsMap=new HashMap<>();
        oddsMap.put("Q6ZX",6);
        playMap.put("rx6",oddsMap) ;
        oddsMap=new HashMap<>();
        oddsMap.put("Q7ZX",7);
        playMap.put("rx7",oddsMap) ;
        oddsMap=new HashMap<>();
        oddsMap.put("Q8ZX",8);
        playMap.put("rx8",oddsMap) ;
        BET_CONTENT_NUM_MAP.put(GROUP_11X5,playMap);

        playMap=new HashMap<>();
        oddsMap=new HashMap<>();
        oddsMap.put("tmbs",3);
        playMap.put("tmbs",oddsMap) ;
        BET_CONTENT_NUM_MAP.put(GROUP_LUCK28,playMap);
    }

}
