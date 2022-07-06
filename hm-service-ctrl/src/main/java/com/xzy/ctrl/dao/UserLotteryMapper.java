package com.xzy.ctrl.dao;

import java.util.List;

import com.xzy.pojo.ctrl.LotteryDO;
import com.xzy.pojo.ctrl.UserLotteryDO;
import com.xzy.pojo.ctrl.vo.LotteryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserLotteryMapper {
    List<LotteryVO> listLotterys(@Param("companyId") Integer companyId,@Param("userId") Integer userId);
    LotteryVO getLottery(@Param("companyId") Integer companyId,@Param("userId") Integer userId, @Param("lotteryId") Integer lotteryId );
    Boolean updateIsLink(@Param("userId") Integer userId, @Param("lotteryId") Integer lotteryId, @Param("isLink") Integer isLink);
    Boolean updateIsReset(@Param("userId") Integer userId,@Param("lotteryId") Integer lotteryId,@Param("isReset") Integer isReset);
    Boolean updateOpenOrClose(@Param("userId") Integer userId,@Param("lotteryId") Integer lotteryId,@Param("isOpen") Integer isOpen);
    Boolean updateOpenOrCloseForUp(@Param("userId") Integer userId,@Param("lotteryId") Integer lotteryId,@Param("isOpen") Integer isOpen);
    Boolean updateOpenOrCloseForUpByGroup(@Param("userId") Integer userId,@Param("groupId") Integer groupId,@Param("isOpen") Integer isOpen);
    Boolean updateDelay(@Param("userId") Integer userId,@Param("lotteryId") Integer lotteryId,@Param("delayOpen") Integer delayOpen,@Param("precedeClose") Integer precedeClose,@Param("zmPrecedeClose") Integer zmPrecedeClose);
    Boolean updateDelayObo(@Param("userId") Integer userId,@Param("lotteryId") Integer lotteryId,@Param("delayType") String delayType,@Param("delay") Integer delay);
    Boolean updateOrdering(@Param("userId") Integer userId, @Param("lotterys") List<LotteryDO> lotterys);
}




