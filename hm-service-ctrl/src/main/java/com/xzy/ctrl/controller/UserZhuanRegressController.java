package com.xzy.ctrl.controller;

import com.xzy.common.model.code.CommonCode;
import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.controller.BaseController;
import com.xzy.ctrl.UserZhuanRegressService;
import com.xzy.pojo.ctrl.request.DiffOddsRQ;
import com.xzy.pojo.ctrl.request.DiffRegressRQ;
import com.xzy.pojo.ctrl.request.OddsRegressLimitFulloddsRQ;
import com.xzy.pojo.ctrl.vo.ZhuanRegressVO;
import com.xzy.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Slf4j
public class UserZhuanRegressController extends BaseController {

    @DubboReference
    private UserZhuanRegressService zhuanRegressService;
    @DubboReference
    private UserService userService;

    @GetMapping("/zhuan-regress")
    public ResponseResult getZhuanRegress(Integer userId) {
        if(userId==null){
            return ResponseData.ERROR(CommonCode.INVALID_PARAM);
        }
        /*
        if(userId!=null){
            UserInfo user=userService.findBaseById(userId);
            if(!logiUserId.equals( user.getParentId())){
                return ResponseData.ERROR(CommonCode.UNAUTHORISE);
            }
        }
         */
        ZhuanRegressVO zhuanRegressVO = zhuanRegressService.getZhuanRegressVO(userId);
        return ResponseData.SUCCESS(zhuanRegressVO);
    }

    /*一级赚二级，二级赚三级赚水*/
    @PutMapping("/zhuan-regress")
    public ResponseResult updateZhuanRegress(@RequestBody OddsRegressLimitFulloddsRQ orlf) {
        orlf.setLoginUserId(getPrimaryId());
        orlf.setUpdateIp(getIpAddr());
        orlf.setUpdateBy(getLoginExt().getUsername());
        zhuanRegressService.updateZhuanRegress(orlf);
        return orlf.getErrcode()==200?ResponseData.SUCCESS(null):ResponseData.ERROR(CommonCode.SERVER_ERROR);
    }

}
