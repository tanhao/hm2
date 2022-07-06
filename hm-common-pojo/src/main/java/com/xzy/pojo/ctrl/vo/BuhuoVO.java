
package com.xzy.pojo.ctrl.vo;

import com.xzy.pojo.ctrl.bo.UserBuhuoStatsBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Setter
@Getter
@ToString
public class BuhuoVO extends KindVO {
    private Map<Integer, Map<Integer, UserBuhuoStatsBO>> buhuoStats;
}