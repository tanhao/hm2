
package com.xzy.pojo.ctrl.vo;

import com.xzy.pojo.ctrl.KindDO;
import com.xzy.pojo.ctrl.PlayDO;
import com.xzy.pojo.ctrl.UserCljpDO;
import com.xzy.pojo.ctrl.response.LogRP;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@ToString
public class LogVO {

    private Map<Integer, List<KindDO>> kinds;
    private Map<Integer, List<PlayDO>> plays;
    private List<LotteryVO> lotterys;
    private Integer kindId;
    private Integer lotteryId;
    private Integer playId;

    private List<LogRP> logs;

    private Integer page;
    private Integer size;
    private Integer total;




}