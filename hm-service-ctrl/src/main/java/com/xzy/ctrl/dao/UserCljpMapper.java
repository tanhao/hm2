package com.xzy.ctrl.dao;

import com.xzy.pojo.ctrl.UserCljpDO;
import com.xzy.pojo.ctrl.UserNoticeDO;
import com.xzy.pojo.ctrl.request.BuhuoRQ;
import com.xzy.pojo.ctrl.request.CljpRQ;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserCljpMapper {
    List<UserCljpDO> listUserCljpDOs(Integer userId,Integer lotteryId,Integer kindId,Integer model);
    void updateCljp(CljpRQ cljp);
    Boolean delCljp(CljpRQ cljp);
}

