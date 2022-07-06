package com.xzy.ctrl;

import com.xzy.pojo.ctrl.UserBuhuoDO;
import com.xzy.pojo.ctrl.UserCljpDO;
import com.xzy.pojo.ctrl.request.BuhuoRQ;
import com.xzy.pojo.ctrl.request.CljpRQ;
import com.xzy.pojo.ctrl.vo.BuhuoVO;
import com.xzy.pojo.ctrl.vo.CljpVO;

import java.util.List;
import java.util.Map;

public interface UserCljpService {

    CljpVO getCljpVO(Integer userId);

    List<UserCljpDO> listUserCljpDOs(Integer userId, Integer lotteryId, Integer kindId,Integer model);

    CljpRQ updateCljp(CljpRQ cljp);

    Boolean delCljp(CljpRQ cljp);
}