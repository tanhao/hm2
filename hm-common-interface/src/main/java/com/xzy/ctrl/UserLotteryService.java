package com.xzy.ctrl;

import java.util.List;
import java.util.Map;

import com.xzy.enums.EUserType;
import com.xzy.pojo.ctrl.LotteryDO;
import com.xzy.pojo.ctrl.UserLotteryDO;
import com.xzy.pojo.ctrl.vo.LotteryVO;
import org.apache.ibatis.annotations.Param;

public interface UserLotteryService {
    List<LotteryVO> listLotteryVOs(Integer userId);
    LotteryVO getLotteryVO(Integer userId,Integer lotteryId);
    Boolean updateIsLink(Integer userId,Integer lotteryId, Boolean isLink);
    Boolean updateIsReset(Integer userId,Integer lotteryId,Boolean isReset);
    Boolean updateOpenOrClose(Integer userId,Integer lotteryId,Boolean isOpen);
    Boolean updateOpenOrCloseForUp(Integer userId,Integer lotteryId,Boolean isOpen);
    Boolean updateOpenOrCloseForUpByGroup(Integer userId,Integer groupId,Boolean isOpen);
    Boolean updateDelay(Integer userId,Integer lotteryId, Integer delayOpen, Integer precedeClose,Integer zmPrecedeClose);
    Boolean updateDelayObo(Integer userId, Integer lotteryId,String delayType, Integer delay);
    Boolean updateOrdering(Integer userId,List<LotteryDO> lotterys);


}