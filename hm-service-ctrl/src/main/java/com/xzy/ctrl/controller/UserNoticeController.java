package com.xzy.ctrl.controller;

import com.xzy.common.model.response.ResponseData;
import com.xzy.controller.BaseController;
import com.xzy.ctrl.UserNoticeService;

import com.xzy.pojo.ctrl.UserNoticeDO;
import com.xzy.pojo.ctrl.bo.UserNoticeBO;
import com.xzy.pojo.ctrl.vo.NoticeVO;
import com.xzy.pojo.ctrl.vo.WarnVO;
import com.xzy.pojo.user.ext.UserLogin;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserNoticeController extends BaseController {

    @DubboReference
    private UserNoticeService userNoticeService;

    @GetMapping("/notice-show")
    public ResponseData getShowNoticeVO(Integer page,Integer size) {
        UserLogin user=getLoginExt();
        NoticeVO noticeVO = userNoticeService.getShowNoticeVO(user.getUserType(),user.getPrimaryId(),page,size);
        return ResponseData.SUCCESS(noticeVO);
    }

    @GetMapping("/notice")
    public ResponseData getNoticeVO(Integer page,Integer size) {
        NoticeVO noticeVO = userNoticeService.getNoticeVO(getPrimaryId(),page,size);
        return ResponseData.SUCCESS(noticeVO);
    }

    @PutMapping("/notice")
    public ResponseData updateNoticeById(@RequestBody UserNoticeDO notice) {
        notice.setUserId(getPrimaryId());
        return ResponseData.SUCCESS(userNoticeService.updateUserNotice(notice));
    }

    @DeleteMapping("/notice")
    public ResponseData deleteNoticeById(@RequestBody UserNoticeDO notice) {
        return ResponseData.SUCCESS(userNoticeService.deleteUserNotice(getPrimaryId(),notice.getId()));
    }

    @PutMapping("/notice/state")
    public ResponseData updateUserNoticeState(@RequestBody UserNoticeDO notice) {
        return ResponseData.SUCCESS(userNoticeService.updateUserNoticeState(getPrimaryId(),notice.getId(),notice.getContent(),notice.getIsAgent()));
    }

    @PostMapping("/notice")
    public ResponseData saveNotice(@RequestBody UserNoticeDO notice) {
        //notice.setUserId(getLoginId());
        notice.setUserId(getPrimaryId());
        return ResponseData.SUCCESS(userNoticeService.saveUserNotice(notice));
    }

}
