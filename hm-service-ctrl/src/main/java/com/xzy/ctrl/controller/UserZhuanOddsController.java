package com.xzy.ctrl.controller;

import com.xzy.common.model.code.CommonCode;
import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.controller.BaseController;
import com.xzy.ctrl.UserZhuanOddsService;
import com.xzy.pojo.ctrl.request.DiffOddsRQ;
import com.xzy.pojo.ctrl.request.OddsRegressLimitFulloddsRQ;
import com.xzy.pojo.ctrl.vo.ZhuanOddsVO;
import com.xzy.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Slf4j
public class UserZhuanOddsController extends BaseController {

    @DubboReference
    private UserZhuanOddsService userZhuanOddsService;
    @DubboReference
    private UserService userService;

    @GetMapping("/zhuan-odds")
    public ResponseResult getZhuanOdds(Integer userId) {

        if(userId==null){
            return ResponseData.ERROR(CommonCode.INVALID_PARAM);
        }
        /*
        if(userId!=null){
            Integer logiUserId=getPrimaryId();
            UserInfo user=userService.findBaseById(userId);
            if(!logiUserId.equals( user.getParentId())){
                return ResponseData.ERROR(CommonCode.UNAUTHORISE);
            }
        }
         */
        ZhuanOddsVO zhuanOddsVO = userZhuanOddsService.getZhuanOddsVO(userId);
        return ResponseData.SUCCESS(zhuanOddsVO);
    }

    /*一级赚二级，二级赚三级赚赔*/
    @PutMapping("/zhuan-odds")
    public ResponseResult updateZhuanOdds(@RequestBody OddsRegressLimitFulloddsRQ orlf) {
        orlf.setLoginUserId(getPrimaryId());
        orlf.setUpdateIp(getIpAddr());
        orlf.setUpdateBy(getLoginExt().getUsername());
        userZhuanOddsService.updateZhuanOdds(orlf);
        return orlf.getErrcode()==200?ResponseData.SUCCESS(null):ResponseData.ERROR(CommonCode.SERVER_ERROR);
    }

}
