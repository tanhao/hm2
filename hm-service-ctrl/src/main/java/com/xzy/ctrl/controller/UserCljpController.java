package com.xzy.ctrl.controller;

import com.xzy.common.model.code.CommonCode;
import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.controller.BaseController;
import com.xzy.ctrl.UserCljpService;
import com.xzy.pojo.ctrl.request.BuhuoRQ;
import com.xzy.pojo.ctrl.request.CljpRQ;
import com.xzy.pojo.ctrl.vo.CljpVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Slf4j
public class UserCljpController extends BaseController {

    @DubboReference
    private UserCljpService userCljpService;

    @GetMapping("/cljp-init")
    public ResponseData getClipVO() {
        CljpVO cljpVO = userCljpService.getCljpVO(getPrimaryId());
        return ResponseData.SUCCESS(cljpVO);
    }

    @GetMapping("/cljp")
    public ResponseData listUserCljpDOs( Integer lotteryId, Integer kindId,Integer model) {
        return ResponseData.SUCCESS(userCljpService.listUserCljpDOs(getPrimaryId(),lotteryId,kindId,model));
    }

    @PutMapping("/cljp")
    public ResponseResult updateCljp(@RequestBody CljpRQ cljp) {
        cljp.setUserId(getPrimaryId());
        userCljpService.updateCljp(cljp);
        return cljp.getErrcode()==200?ResponseData.SUCCESS(null):ResponseData.ERROR(CommonCode.SERVER_ERROR);
    }

    @DeleteMapping("/cljp")
    public ResponseResult dekCljp(@RequestBody CljpRQ cljp) {
        cljp.setUserId(getPrimaryId());
        Boolean isSucdess=userCljpService.delCljp(cljp);
        return isSucdess?ResponseData.SUCCESS(null):ResponseData.ERROR(CommonCode.SERVER_ERROR);
    }

}
