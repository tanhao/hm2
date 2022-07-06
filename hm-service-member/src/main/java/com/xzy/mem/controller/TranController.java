package com.xzy.mem.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.MemberCode;
import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.controller.BaseController;
import com.xzy.mem.service.TranQueueService;
import com.xzy.member.TransactionService;
import com.xzy.pojo.mem.BetRq;
import com.xzy.pojo.mem.OddsRq;
import com.xzy.pojo.user.ext.UserLogin;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/trans")
@Slf4j
public class TranController extends BaseController {
    @Autowired
    TranQueueService tranQueueService;
    @DubboReference
    TransactionService transactionService;

    @PostMapping("/task/bet")
    public ResponseResult taskBet(@RequestBody @Valid BetRq betRq, BindingResult result) {
        try {
            UserLogin loginExt = getLoginExt();
            Integer loginId = loginExt.getUserId();
            Integer companyId = loginExt.getCompanyId();
            Date resultAccountDay = transactionService.checkBetTime(betRq.getLotteryId(), companyId, betRq.getGameNo());
            betRq.setResultAccountDay(resultAccountDay);
            if(result.hasErrors()){
                JSONObject obj = getErrorSON(result);
                return ResponseData.ERROR(obj);
            }
            if(StrUtil.isBlank(betRq.getBetSource())){
                betRq.setBetSource("api");
            }else if(!betRq.getBetSource().equals("normal")){
                betRq.setBetSource("normal");
            }
            tranQueueService.tranQueueBet(loginId, betRq);
        } catch (ExecutionException e) {
            e.printStackTrace();
            ExceptionCast.cast(MemberCode.BET_ERROR);
        } catch (InterruptedException e) {
            e.printStackTrace();
            ExceptionCast.cast(MemberCode.BET_ERROR);
        }
        return ResponseData.SUCCESS();
    }


    private JSONObject getErrorSON(BindingResult bindingResult){
        JSONObject obj = new JSONObject();
        bindingResult.getAllErrors().stream().forEach(
                error->{
                    FieldError fieldError = (FieldError)error;
                    obj.put(MemberCode.BET_INFO_ERROR.code()+"",new StringBuilder().append(fieldError.getField()).append(" : ").append(fieldError.getDefaultMessage()));
                }
        );
        return obj;
    }

}
