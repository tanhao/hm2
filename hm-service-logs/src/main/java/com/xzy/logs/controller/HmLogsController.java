package com.xzy.logs.controller;

import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.controller.BaseController;
import com.xzy.logs.HmLogsService;
import com.xzy.logs.IpaddressService;
import com.xzy.pojo.logs.HmLogs;
import com.xzy.pojo.logs.HmUserSonLog;
import com.xzy.pojo.logs.IpAddress;
import com.xzy.pojo.logs.request.HmLogsList;
import com.xzy.pojo.logs.request.HmLogsRq;
import com.xzy.pojo.user.ext.UserLogin;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/hmLogs")
@Slf4j
public class HmLogsController extends BaseController {

    @DubboReference
    private HmLogsService logsService;

    @DubboReference
    private IpaddressService ipaddressService;


    /**
     * 添加操作记录
     *
     * @param logs
     * @return
     */
    @PostMapping("/addLogs")
    public ResponseResult addLogs(@RequestBody HmLogs logs) {
        UserLogin userLogin = getLoginExt();
        logs.setCreateUser(userLogin.getUsername());//创建人
        if (userLogin.getEnabledSon()) {//是否是子帐号
            logs.setCreateId(userLogin.getPrimaryId().toString());//创建人ID
        } else {
            logs.setCreateId(userLogin.getUserId().toString());//创建人ID
        }
        logs.setCreateIp(getIpAddr());//IP
        logs.setCreateTime(new Date());//创建时间
        logsService.addHmLogs(logs);
        return ResponseData.SUCCESS();
    }

    /**
     * 添加子帐号操作记录
     */
    @PostMapping("/addUserSonLog")
    public ResponseResult addUserSonLog(@RequestBody HmUserSonLog logs) {
        System.out.println("添加子帐号操作记录");
        UserLogin userLogin = getLoginExt();
        logs.setCreateUser(userLogin.getUsername());//创建人
        logs.setCreateId(userLogin.getPrimaryId().toString());//创建人ID
        logs.setCreateIp(getIpAddr());//IP
        logs.setCreateTime(new Date());//创建时间
        logsService.addUserSonLog(logs);
        return ResponseData.SUCCESS();
    }

    /**
     * 按USER ID查询
     *
     * @param hmLogsRq
     * @return
     */
    @GetMapping("/selHmLogsIdList")
    public ResponseResult selHmLogsIdList(HmLogsRq hmLogsRq) {
        return ResponseData.SUCCESS_LIST(logsService.getHmLogsList(hmLogsRq));
    }


    /**
     * 按USER ID查询 子帐号
     */
    @GetMapping("/selHmUserSonLogIdList")
    public ResponseResult selHmUserSonLogIdList(HmLogsRq hmLogsRq) {
        return ResponseData.SUCCESS_LIST(logsService.selHmUserSonLogIdList(hmLogsRq));
    }

    @PostMapping("/push/list")
    public ResponseResult pushRecordLogList(@RequestBody HmLogsList hmLogsList) {
        UserLogin userLogin = getLoginExt();
        hmLogsList.getHmLogs().forEach(recordLog -> {
            if (userLogin.getEnabledSon()) {//是否是子帐号
                recordLog.setCreateId(userLogin.getPrimaryId().toString());//创建人ID
            } else {
                recordLog.setCreateId(userLogin.getUserId().toString());//创建人ID
            }
            recordLog.setCreateUser(userLogin.getUsername());
            recordLog.setCreateIp(getIpAddr());
            recordLog.setCreateTime(new Date());
        });
        logsService.insertRecordLogs(hmLogsList.getHmLogs());
        return ResponseData.SUCCESS();
    }

    @GetMapping("/logs/ips/list")
    public ResponseResult getIps(String[] ips){
        List<IpAddress> recordLogs = ipaddressService.selectByIps(ips);
        return ResponseData.SUCCESS_LIST(recordLogs);
    }

}
