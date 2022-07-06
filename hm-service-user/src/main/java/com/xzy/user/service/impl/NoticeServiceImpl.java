package com.xzy.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CommonCode;
import com.xzy.common.model.code.HmResultCode;
import com.xzy.pojo.user.HmNotice;
import com.xzy.pojo.user.code.UcenterCode;
import com.xzy.user.NoticeService;
import com.xzy.user.dao.NoticeMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DubboService
@Slf4j
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    NoticeMapper noticeMapper;

    /**
     * 添加通知
     * @param notice
     */
    public void addNotice(HmNotice notice){
        log.info("添加信息参数：{}", notice);
        ExceptionCast.castCheck(StrUtil.isBlank(notice.getType()), HmResultCode.NO_NOTICE_TYPE);
        ExceptionCast.castCheck(StrUtil.isBlank(notice.getContent()),HmResultCode.NO_NOTICE_CONTENT);
        noticeMapper.insert(notice);
    }

    /**
     * 修改通知
     * @param notice
     */
    public void updateNotice(HmNotice notice){
        log.info("修改信息参数：{}", notice);
        //判断是否有修改用户的ID
        ExceptionCast.castCheck(ObjectUtil.isNull(notice.getUserId()), CommonCode.INVALID_PARAM);
    }

    /**
     * 查询所有 通知
     */
    public List<HmNotice> selNoticeAll(){
        return noticeMapper.selectAll();
    }

    /**
     * 按 USER_ID 查询
     */
    public List<HmNotice> selNoticeUserId(Integer userId){
        return noticeMapper.selectByExample(userId);
    }

}
