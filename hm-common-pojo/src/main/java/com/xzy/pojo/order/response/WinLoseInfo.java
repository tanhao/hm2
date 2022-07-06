package com.xzy.pojo.order.response;

import lombok.Data;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;

@Data
public class WinLoseInfo {
    private Integer userId;
    private Integer parentId;
    private String lotteryId;
    private Integer oddsId;
    private String oddsKey;
    private Integer playId;
    private String playKey;
    private String kindKey;
    private Integer kindId;
    private String username;
    private String userType;
    private String nickName;
    private Integer userLevel;
    private String lotteryKey;
    private BigDecimal betAmt;
    private Integer betCount;
    private BigDecimal betWinAmt;
    private BigDecimal betOddsAmt;
    private BigDecimal toLowerAmt;
    private BigDecimal toLowerWin;
    private BigDecimal toLowerComm;
    private BigDecimal ptBetAmt;
    private BigDecimal bhBetPtAmt;
    private BigDecimal actualPtBetAmt;
    private BigDecimal actualWinAmt;
    private BigDecimal actualCommAmt;
    private BigDecimal gainCommAmt;
    private BigDecimal gainOddsAmt;
    private BigDecimal toUperAmt;
    private BigDecimal toUperWin;
    private BigDecimal actualResult;
    private BigDecimal gainCommOdds;

    private BigDecimal bhAmt;
    private BigDecimal bhWinAmt;
    private BigDecimal bhComm;
    private BigDecimal bhTotal;

    public WinLoseInfo addBigDec(WinLoseInfo winLoseInfo) {
        try {
            Field[] field = winLoseInfo.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
            for (int j = 0; j < field.length; j++) {     //遍历所有属性
                String name = field[j].getName();    //获取属性的名字
                name = name.substring(0, 1).toUpperCase() + name.substring(1); //将属性的首字符大写，方便构造get，set方法
                String type = field[j].getGenericType().toString();    //获取属性的类型
                if (type.equals("class java.math.BigDecimal")) {
                    Method m = winLoseInfo.getClass().getMethod("get" + name);
                    BigDecimal value = (BigDecimal) m.invoke(winLoseInfo);    //调用getter方法获取属性值
                    Method setMethod = winLoseInfo.getClass().getMethod("set" + name, BigDecimal.class);
                    BigDecimal big = (BigDecimal) this.getClass().getMethod("get" + name).invoke(this);
                    if(value!=null){
                        setMethod.invoke(winLoseInfo, value.add(big == null ? BigDecimal.ZERO : big));
                    }
                }
                if (name.equals("BetCount")) {
                    winLoseInfo.betCount = winLoseInfo.betCount + this.betCount;
                }
            }
            return winLoseInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return winLoseInfo;
    }
}
