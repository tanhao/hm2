package com.xzy.ctrl.dao;

import java.util.List;

import com.xzy.pojo.ctrl.UserJumpDO;
import com.xzy.pojo.ctrl.request.CljpRQ;
import com.xzy.pojo.ctrl.request.JumpRQ;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserJumpMapper {
    List<UserJumpDO> listUserJumpDOs(Integer userId);
    void updateJump(JumpRQ jumpRQ);
}
