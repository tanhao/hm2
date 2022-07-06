package com.xzy.user.controller;


import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.controller.BaseController;
import com.xzy.pojo.user.HmNotice;
import com.xzy.user.NoticeService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("notice")
@CrossOrigin
public class NoticeController extends BaseController{

    @DubboReference
    private NoticeService noticeService;
    /**
     * 添加通知
     */
    @PostMapping("/addNotice")
    public ResponseResult add(@RequestBody HmNotice notice) {
        notice.setCreateTime(new Date());
        notice.setStartTime(new Date());
        notice.setEndTime(new Date());
        notice.setCreateBy(getLoginExt().getUsername());
        noticeService.addNotice(notice);
        return ResponseData.SUCCESS();
    }

    /**
     * 修改通知
     */
    @PostMapping("/updateNotice")
    public ResponseResult updateNotice(@RequestBody HmNotice notice) {
        noticeService.updateNotice(notice);
        return ResponseData.SUCCESS();
    }

    /**
     * 查询所有 通知
     */
    @GetMapping("/selNoticeAll")
    public ResponseResult selNoticeAll() {
        return ResponseData.SUCCESS(noticeService.selNoticeAll());
    }

    /**
     * 按 USER_ID 查询
     */
    @GetMapping("/selNoticeUserId")
    public ResponseResult selNoticeUserId(Integer userId){
        return ResponseData.SUCCESS(noticeService.selNoticeUserId(userId));
    }

}
