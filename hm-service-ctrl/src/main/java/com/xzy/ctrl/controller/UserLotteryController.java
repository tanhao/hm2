package com.xzy.ctrl.controller;

import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CommonCode;
import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.controller.BaseController;
import com.xzy.ctrl.LotteryService;
import com.xzy.ctrl.UserLimitService;
import com.xzy.ctrl.UserLotteryService;
import com.xzy.enums.EOpenModel;
import com.xzy.enums.EUserType;
import com.xzy.pojo.ctrl.request.UserLotteryRQ;
import com.xzy.pojo.ctrl.vo.LotteryVO;
import com.xzy.pojo.user.ext.UserLogin;
import com.xzy.pojo.user.response.UserLotteryLimitRp;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@Slf4j
public class UserLotteryController extends BaseController {

    @DubboReference
    private UserLotteryService userLotteryService;
    @DubboReference
    private UserLimitService userLimitServicce;
    @DubboReference
    private LotteryService lotteryService;

    @GetMapping("/lottery")
    public ResponseData listLotteryVOs() {
        List<LotteryVO> list = userLotteryService.listLotteryVOs(getPrimaryId());
        return ResponseData.SUCCESS(list);
    }
    @GetMapping("/company/lottery")
    public ResponseData listLotteryByCompany() {
        List<LotteryVO> list = lotteryService.selectLotteryByCompanyId(getLoginExt().getCompanyId());
        return ResponseData.SUCCESS(list);
    }

    @GetMapping("/lottery/{lotteryId}")
    public ResponseData getLottery(@PathVariable Integer lotteryId) {
        LotteryVO lottery = userLotteryService.getLotteryVO(getPrimaryId(),lotteryId);
        return ResponseData.SUCCESS(lottery);
    }

    @GetMapping("/lotteryLimit/{lotteryId}")
    public ResponseData getUserLotteryLimit(@PathVariable Integer lotteryId) {
        List<UserLotteryLimitRp> list = userLimitServicce.getUserLotteryLimit(getLoginId(),lotteryId);
        return ResponseData.SUCCESS(list);
    }


    @PutMapping("/lottery/status")
    public ResponseResult updateOpenOrClose(@RequestBody UserLotteryRQ l) {
        Boolean isSuccess = userLotteryService.updateOpenOrClose(getPrimaryId(), l.getLotteryId(), l.getIsOpen());
        return isSuccess ? ResponseResult.SUCCESS() : ResponseData.FAIL();
    }

    @PutMapping("/lottery/link")
    public ResponseResult udpateIsLink(@RequestBody  UserLotteryRQ l) {
        boolean isSuccess = userLotteryService.updateIsLink(getPrimaryId(), l.getLotteryId(), l.getIsLink());
        return isSuccess ? ResponseData.SUCCESS() : ResponseData.FAIL();
    }
    @PutMapping("/lottery/open-model")
    public ResponseResult udpateOpenModel(@RequestBody  UserLotteryRQ l) {
        UserLogin user=getLoginExt();
        EUserType userType=EUserType.valueOf(user.getUserLevel());
        if(!EUserType.GROUP.equals(userType)){
            ExceptionCast.cast(CommonCode.UNAUTHORISE);
        }
        EOpenModel openModel=null;
        try{
            openModel=EOpenModel.valueOf(l.getOpenModel());
        }catch (Exception e){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        boolean isSuccess = lotteryService.updateOpenModel(l.getLotteryId(),openModel);
        return isSuccess ? ResponseData.SUCCESS() : ResponseData.FAIL();
    }

    @PutMapping("/lottery/reset")
    public ResponseResult updateIsReset(@RequestBody  UserLotteryRQ l) {
        boolean isSuccess = userLotteryService.updateIsReset(getPrimaryId(), l.getLotteryId(), l.getIsReset());
        return isSuccess ? ResponseData.SUCCESS() : ResponseData.FAIL();
    }
    @PutMapping("/lottery/delay")
    public ResponseResult updateDelay(@RequestBody  UserLotteryRQ l) {
        UserLogin user=getLoginExt();
        if(EUserType.GROUP.equals(EUserType.valueOf(user.getUserLevel()))){
            boolean isSuccess = lotteryService.updateDelay(l.getLotteryId(),l.getDelayOpen(),l.getPrecedeClose(),l.getZmPrecedeClose());
            return isSuccess ? ResponseData.SUCCESS() : ResponseData.FAIL();
        }else if(EUserType.COMPANY.equals(EUserType.valueOf(user.getUserLevel()))){
            boolean isSuccess = userLotteryService.updateDelay(getPrimaryId(), l.getLotteryId(),l.getDelayOpen(),l.getPrecedeClose(),l.getZmPrecedeClose());
            return isSuccess ? ResponseData.SUCCESS() : ResponseData.FAIL();
        }
        return ResponseData.SUCCESS();
    }

    @PutMapping("/lottery/delay-obo")
    public ResponseResult updateDelayObo(@RequestBody  UserLotteryRQ l) {
        UserLogin user=getLoginExt();
        if(EUserType.COMPANY.equals(EUserType.valueOf(user.getUserLevel()))){
            boolean isSuccess = userLotteryService.updateDelayObo(getPrimaryId(), l.getLotteryId(),l.getOpenModel(),l.getDelayOpen());
            if(isSuccess){
                LotteryVO lottery= userLotteryService.getLotteryVO(getPrimaryId(),l.getLotteryId());
                return  ResponseData.SUCCESS(lottery);
            }else{
                return ResponseData.FAIL();
            }


        }
        return ResponseData.SUCCESS();
    }

    @PutMapping("/lottery/ordering")
    public ResponseResult updateOrdering(@RequestBody  UserLotteryRQ l) {
        UserLogin user=getLoginExt();
        if(EUserType.GROUP.equals(EUserType.valueOf(user.getUserLevel()))){
            boolean isSuccess = lotteryService.updateOrdering(l.getLotterys());
            return isSuccess ? ResponseData.SUCCESS() : ResponseData.FAIL();
        }else if(EUserType.COMPANY.equals(EUserType.valueOf(user.getUserLevel()))){
            boolean isSuccess = userLotteryService.updateOrdering(getPrimaryId(), l.getLotterys());
            return isSuccess ? ResponseData.SUCCESS() : ResponseData.FAIL();
        }
        return ResponseData.SUCCESS();


    }

    @PutMapping("/lottery/status-up")
    public ResponseResult updateOpenOrCloseForUp(@RequestBody  UserLotteryRQ l) {
        boolean isSuccess = userLotteryService.updateOpenOrCloseForUp(getPrimaryId(), l.getLotteryId(),l.getIsOpen());
        return isSuccess ? ResponseData.SUCCESS() : ResponseData.FAIL();
    }
    @PutMapping("/lottery/status-up-group")
    public ResponseResult updateOpenOrCloseForUpByGroup(@RequestBody  UserLotteryRQ l) {
        boolean isSuccess = userLotteryService.updateOpenOrCloseForUpByGroup(getPrimaryId(), l.getGroupId(),l.getIsOpen());
        return isSuccess ? ResponseData.SUCCESS() : ResponseData.FAIL();
    }








}
