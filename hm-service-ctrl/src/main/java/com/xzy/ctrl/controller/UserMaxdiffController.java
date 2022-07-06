package com.xzy.ctrl.controller;

import com.xzy.common.model.code.CommonCode;
import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.controller.BaseController;
import com.xzy.ctrl.UserMaxdiffService;
import com.xzy.ctrl.UserZhuanRegressService;
import com.xzy.pojo.ctrl.request.DiffRegressRQ;
import com.xzy.pojo.ctrl.request.MaxdiffRQ;
import com.xzy.pojo.ctrl.request.OddsRegressLimitFulloddsRQ;
import com.xzy.pojo.ctrl.vo.MaxdiffVO;
import com.xzy.pojo.ctrl.vo.ZhuanRegressVO;
import com.xzy.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Slf4j
public class UserMaxdiffController extends BaseController {

    @DubboReference
    private UserMaxdiffService userMaxdiffService;
    @DubboReference
    private UserService userService;

    @GetMapping("/maxdiff")
    public ResponseResult getMaxdiff() {
        MaxdiffVO maxdiffVO = userMaxdiffService.getMaxdiffVO(getPrimaryId());
        return ResponseData.SUCCESS(maxdiffVO);
    }

    /*公司改一级最大賺賠*/
    @PutMapping("/maxdiff")
    public ResponseResult updateMaxdiff(@RequestBody MaxdiffRQ maxdiff) {
        maxdiff.setUserId(getPrimaryId());
        userMaxdiffService.updateCompanyMaxdiff(maxdiff);
        return maxdiff.getErrcode()==200?ResponseData.SUCCESS(null):ResponseData.ERROR(CommonCode.SERVER_ERROR);
    }


}
