package com.xzy.ctrl.controller;

import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CommonCode;
import com.xzy.common.model.code.CtrlCode;
import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.common.model.response.ResultCode;
import com.xzy.controller.BaseController;
import com.xzy.ctrl.UserOddsService;
import com.xzy.enums.EUserType;
import com.xzy.pojo.ctrl.request.CopyORLFRQ;
import com.xzy.pojo.ctrl.request.OddsRegressLimitFulloddsRQ;
import com.xzy.pojo.ctrl.vo.OddsVO;
import com.xzy.pojo.user.ext.UserLogin;
import com.xzy.pojo.user.response.UserInfo;
import com.xzy.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Slf4j
public class UserOddsController extends BaseController {

    @DubboReference
    private UserOddsService userOddsService;
    @DubboReference
    private UserService userService;

    @GetMapping("/odds")
    public ResponseResult getOddsVO(Integer userId) {
        Integer logiUserId=getPrimaryId();
        Integer logic=userId==null?getPrimaryId():userId;
        OddsVO oddsVO = userOddsService.getOddsVO(logic);
        return ResponseData.SUCCESS(oddsVO);
    }
    //新增一级时退水赔率
    @GetMapping("/agent1-orl")
    public ResponseResult getAgent1ORL(Integer userId) {
        Integer logiUserId=getPrimaryId();
        Integer logic=userId==null?getPrimaryId():userId;
        OddsVO oddsVO = userOddsService.getAgent1ORL(logic);
        return ResponseData.SUCCESS(oddsVO);
    }

    @GetMapping("/check-safe-pwd")
    public ResponseResult checkSafePwd(String pwd) {
        Boolean success = userService.checkSecondary(getPrimaryId(),pwd);
        return success?ResponseData.SUCCESS():ResponseData.ERROR(CtrlCode.SECURITY_CODE_ERROR);
    }

    /*后桶or公司*/
    @PutMapping("/orlf")
    public ResponseResult updateORCL(@RequestBody OddsRegressLimitFulloddsRQ orlf) {
        UserLogin user=getLoginExt();
        EUserType userType=EUserType.valueOf(user.getUserLevel());
        orlf.setUserId(user.getPrimaryId());
        if(EUserType.GROUP.equals(userType)){
            userOddsService.updateORCLHoutong(orlf);
        }else if(EUserType.COMPANY.equals(userType)){
            userOddsService.updateORCLCompany(orlf);
        }else{
            ExceptionCast.cast(CommonCode.UNAUTHORISE);
        }
        return orlf.getErrcode()==200?ResponseData.SUCCESS(null):ResponseData.ERROR(CommonCode.SERVER_ERROR);
    }



    /*公司没开一级之前后桶改公司数据*/
    @PutMapping("/compnay-orlf")
    public ResponseResult updateCompanyORLF(@RequestBody OddsRegressLimitFulloddsRQ orlf) {
        UserLogin user=getLoginExt();
        if(!EUserType.GROUP.equals(EUserType.valueOf( user.getUserLevel()))){
            ExceptionCast.cast(CommonCode.UNAUTHORISE);
        }
        orlf.setLoginUserId(user.getPrimaryId());
        userOddsService.updateCompanyORLF(orlf);
        return orlf.getErrcode()==200?ResponseData.SUCCESS(null):ResponseData.ERROR(CtrlCode.valueOf(orlf.getErrcode()));
    }

    /*公司改一级赔率*/
    @PutMapping("/agent1-orl")
    public ResponseResult updateAgent1ORL(@RequestBody OddsRegressLimitFulloddsRQ orlf) {
        UserLogin user=getLoginExt();
        if(!EUserType.COMPANY.equals(EUserType.valueOf( user.getUserLevel()))){
            ExceptionCast.cast(CommonCode.UNAUTHORISE);
        }
        orlf.setUpdateIp(getIpAddr());
        orlf.setUpdateBy(getLoginExt().getUsername());
        orlf.setLoginUserId(user.getPrimaryId());
        userOddsService.updateAgent1ORL(orlf);
        return orlf.getErrcode()==200?ResponseData.SUCCESS(null):ResponseData.ERROR(CommonCode.SERVER_ERROR);
    }

    @PutMapping("/agent1-regress-limit")
    public ResponseResult updateAgent1RegressLimit(@RequestBody OddsRegressLimitFulloddsRQ orlf) {
        UserLogin user=getLoginExt();
        if(!EUserType.COMPANY.equals(EUserType.valueOf( user.getUserLevel()))){
            ExceptionCast.cast(CommonCode.UNAUTHORISE);
        }
        orlf.setUpdateIp(getIpAddr());
        orlf.setUpdateBy(getLoginExt().getUsername());
        orlf.setLoginUserId(user.getPrimaryId());
        userOddsService.updateAgent1RegressLimit(orlf);
        return orlf.getErrcode()==200?ResponseData.SUCCESS(null):ResponseData.ERROR(CommonCode.SERVER_ERROR);
    }


    /*后桶复制公司退水限注到另一个公司*/
    @PutMapping("/copy-company")
    public ResponseResult copyCompany(@RequestBody CopyORLFRQ copy) {
        UserLogin user=getLoginExt();
        if(!EUserType.GROUP.equals(EUserType.valueOf( user.getUserLevel()))){
            ExceptionCast.cast(CommonCode.UNAUTHORISE);
        }
        userOddsService.copyCompany(copy);
        return copy.getErrcode()==200?ResponseData.SUCCESS(null):ResponseData.ERROR(CtrlCode.valueOf(copy.getErrcode()));
    }
    /*公司复制一级的退水限注到另一个一级*/
    @PutMapping("/copy-agent1")
    public ResponseResult copyAgent1(@RequestBody CopyORLFRQ copy) {
        UserLogin user=getLoginExt();
        if(!EUserType.COMPANY.equals(EUserType.valueOf( user.getUserLevel()))){
            ExceptionCast.cast(CommonCode.UNAUTHORISE);
        }
        userOddsService.copyAgent1(copy);
        return copy.getErrcode()==200?ResponseData.SUCCESS(null):ResponseData.ERROR(CtrlCode.valueOf(copy.getErrcode()));
    }



}
