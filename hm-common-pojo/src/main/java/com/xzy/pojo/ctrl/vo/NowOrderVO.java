package com.xzy.pojo.ctrl.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.xzy.pojo.ctrl.*;

import com.xzy.pojo.ctrl.bo.UserOddsBO;
import com.xzy.pojo.ctrl.bo.UserStatsBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class NowOrderVO implements Serializable {
    private LotteryVO lottery;
    private List<LotteryVO> lotterys;
    private Map<Integer, KindDO> kinds;
    private Map<Integer, CategoryDO> categorys;
    private Map<Integer, PlayDO> plays;
    private List<OddsDO> oddss;

    private Map<Integer, double[]> userOddss;
    private Map<Integer, double[]> userOddsNows;
    private Map<Integer, double[]> userOddsJumps;
    private Map<Integer, double[]> userOddsCljps;
    private Map<Integer, Boolean> userOddsCloses;

    private Map<Integer, UserStepDO> userSteps;
    private Map<Integer, UserStatsBO> userStats;
    private Map<Integer, UserStatsBO> userStatsLottery;

    private String market;
    private String buhuo;
    private Map<String,Boolean> markets;

    private Double winlose=0D;

    private Boolean canEdit;
    private Boolean canCloseOpen;


}