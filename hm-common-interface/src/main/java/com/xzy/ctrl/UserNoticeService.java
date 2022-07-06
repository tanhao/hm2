package com.xzy.ctrl;

import com.xzy.pojo.ctrl.UserNoticeDO;
import com.xzy.pojo.ctrl.bo.UserNoticeBO;
import com.xzy.pojo.ctrl.vo.NoticeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserNoticeService {
    public NoticeVO getNoticeVO(Integer userId,Integer page,Integer size);

    public NoticeVO getShowNoticeVO(String type, Integer userId,Integer page,Integer size);

    public List<UserNoticeBO> listShowNotice(String type, Integer userId, Integer page, Integer size);

    public int updateUserNotice(UserNoticeDO notice);

    public int deleteUserNotice(Integer userId,Integer id);

    public int updateUserNoticeState(Integer userId,Integer id,String type,Boolean val);

    public int saveUserNotice(UserNoticeDO notice);
}