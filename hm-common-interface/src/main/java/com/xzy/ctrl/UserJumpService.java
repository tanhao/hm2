package com.xzy.ctrl;

import java.util.List;

import com.xzy.pojo.ctrl.UserJumpDO;
import com.xzy.pojo.ctrl.request.CljpRQ;
import com.xzy.pojo.ctrl.request.JumpRQ;
import com.xzy.pojo.ctrl.vo.JumpVO;

public interface UserJumpService {
    public JumpVO getJumpVO(Integer userId);

    JumpRQ updateJump(JumpRQ jump);
}