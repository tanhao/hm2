package com.xzy.ctrl;

import java.util.List;

import com.xzy.pojo.ctrl.UserJumpDO;
import com.xzy.pojo.ctrl.UserWarnDO;
import com.xzy.pojo.ctrl.vo.JumpVO;
import com.xzy.pojo.ctrl.vo.WarnVO;

public interface UserWarnService {
    public WarnVO getWarnVO(Integer userId);
}