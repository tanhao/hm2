package com.xzy.ctrl;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CtrlCode;
import com.xzy.pojo.ctrl.UserBuhuoDO;
import com.xzy.pojo.ctrl.UserStepDO;
import com.xzy.pojo.ctrl.request.OddsRegressLimitFulloddsRQ;
import com.xzy.pojo.ctrl.vo.StepVO;
import org.apache.ibatis.annotations.Param;

public interface UserStepService {
    StepVO getStepVO(Integer userId);

    List<UserStepDO> listUserStepDOsByLotteryId(Integer userId, Integer lotteryId);


    OddsRegressLimitFulloddsRQ updateStep(OddsRegressLimitFulloddsRQ orlf);
}