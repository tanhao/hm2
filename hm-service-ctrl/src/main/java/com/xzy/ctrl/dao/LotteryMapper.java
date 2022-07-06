package com.xzy.ctrl.dao;

import com.xzy.pojo.ctrl.LotteryDO;
import com.xzy.pojo.ctrl.vo.LotteryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LotteryMapper {
    List<LotteryVO> selectLotteryByCompanyId(@Param("companyId") Integer companyId );
    List<LotteryVO> listLotterys();
    LotteryVO getLottery(@Param("lotteryId") Integer lotteryId );
    Boolean updateDelay(@Param("lotteryId") Integer lotteryId, @Param("delayOpen") Integer delayOpen, @Param("precedeClose") Integer precedeClose, @Param("zmPrecedeClose") Integer zmPrecedeClose);
    Boolean updateOrdering(@Param("lotterys") List<LotteryDO> lotterys);
    Boolean updateOpenModel(@Param("lotteryId") Integer lotteryId, @Param("openModel") String openModel);


}
