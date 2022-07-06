package com.xzy.ctrl;

import com.xzy.pojo.ctrl.request.BuhuoRQ;
import com.xzy.pojo.ctrl.vo.BuhuoVO;

public interface UserBuhuoService {
    BuhuoVO getBuhuoVO(Integer userId);
    BuhuoRQ updateBuhuo(BuhuoRQ buhuo);
}