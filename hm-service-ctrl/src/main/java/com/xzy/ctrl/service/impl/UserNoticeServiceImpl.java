package com.xzy.ctrl.service.impl;

import com.xzy.ctrl.UserNoticeService;
import com.xzy.ctrl.dao.UserNoticeMapper;
import com.xzy.pojo.ctrl.UserNoticeDO;
import com.xzy.pojo.ctrl.bo.UserNoticeBO;
import com.xzy.pojo.ctrl.vo.NoticeVO;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * 公告业务逻辑层
 */
@DubboService
@SuppressWarnings("all")
public class UserNoticeServiceImpl implements UserNoticeService {

    @Autowired
    private UserNoticeMapper userNoticeMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public NoticeVO getNoticeVO(Integer userId,Integer page,Integer size) {
        List<UserNoticeBO> listUserNoteicBOs=userNoticeMapper.listUserNotices(userId,(page-1)*size,size*page);
        Integer total=userNoticeMapper.getUserNoticeTotal(userId);
        NoticeVO noticeVO=new NoticeVO();
        noticeVO.setPage(page);
        noticeVO.setSize(size);
        noticeVO.setTotal(total);
        noticeVO.setNotices(listUserNoteicBOs);
        return noticeVO;
    }

    @Override
    public NoticeVO getShowNoticeVO(String type, Integer userId, Integer page, Integer size) {
        List<UserNoticeBO> listUserNoteicBOs=userNoticeMapper.listShowNotice(type,userId,(page-1)*size,size*page);
        Integer total=userNoticeMapper.getShowNoticeTotal(type,userId);
        NoticeVO noticeVO=new NoticeVO();
        noticeVO.setPage(page);
        noticeVO.setSize(size);
        noticeVO.setTotal(total);
        noticeVO.setNotices(listUserNoteicBOs);
        return noticeVO;
    }

    @Override
    public List<UserNoticeBO> listShowNotice(String type, Integer userId, Integer page, Integer size) {
        return userNoticeMapper.listShowNotice(type,userId,(page-1)*size,size*page);
    }

    @Override
    public int updateUserNotice(UserNoticeDO notice) {
        return userNoticeMapper.updateUserNotice(notice);
    }

    @Override
    public int deleteUserNotice(Integer userId,Integer id) {
        return userNoticeMapper.deleteUserNotice(userId,id);
    }

    @Override
    public int updateUserNoticeState(Integer userId,Integer id,String type,Boolean val) {
        return userNoticeMapper.updateUserNoticeState(userId,id,type,val?1:0);
    }

    @Override
    public int saveUserNotice(UserNoticeDO notice) {
        return userNoticeMapper.saveUserNotice(notice);
    }
}
