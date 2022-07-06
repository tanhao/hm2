package com.xzy.ctrl.controller;

import com.xzy.common.model.code.CtrlCode;
import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.controller.BaseController;
import com.xzy.ctrl.NowOrderService;
import com.xzy.ctrl.UserOddsService;
import com.xzy.pojo.ctrl.request.ManualBuhuoRQ;
import com.xzy.pojo.ctrl.request.OddsCloseRQ;
import com.xzy.pojo.ctrl.request.OddsPlusMinusRQ;
import com.xzy.pojo.ctrl.vo.NowOrderVO;
import com.xzy.pojo.user.ext.UserLogin;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class NowOrderController extends BaseController {

    @DubboReference
    private NowOrderService nowOrderService;
    @DubboReference
    private UserOddsService userOddsService;

    @GetMapping("/now-front")
    public ResponseData getFrontOdds(Integer userId,Integer lotteryId) {
        Integer loginUserId=getPrimaryId();
        NowOrderVO nowOrderVO= nowOrderService.getFrontOdds(userId==null?loginUserId:userId,lotteryId);
        return ResponseData.SUCCESS(nowOrderVO);
    }

    
    @GetMapping("/now-stats-front")
    public ResponseData getFrontNowStats(Integer userId,Integer lotteryId) {
        Integer loginUserId=getPrimaryId();
        NowOrderVO nowOrderVO= nowOrderService.getFrontNowStats(userId==null?loginUserId:userId,lotteryId);
        return ResponseData.SUCCESS(nowOrderVO);
    }

    @GetMapping("/now")
    public ResponseData getNowOrderVO(Integer userId,Integer lotteryId,String market,String markets,String buhuo) {
        Integer loginUserId=getPrimaryId();
        NowOrderVO nowOrderVO= nowOrderService.getNowOrderVO(loginUserId,userId==null?loginUserId:userId,lotteryId,market,markets,buhuo);
        //NowOrderVO nowOrderVO= nowOrderService.getNowOrderVO(3386,201,"A");
        return ResponseData.SUCCESS(nowOrderVO);
    }

    @PutMapping("/plus-minus-odds")
    public ResponseResult updateNowOdds(@RequestBody OddsPlusMinusRQ pmos) {
        UserLogin user=getLoginExt();
        pmos.setUserId(getPrimaryId());
        pmos.setUserLevel(user.getUserLevel());
        pmos.setUpdateIp(getIpAddr());
        pmos.setUpdateBy(getLoginExt().getUsername());
        userOddsService.updateUserOdds(pmos);
        return pmos.getErrcode() == 200 ? ResponseData.SUCCESS(null) : ResponseData.ERROR(CtrlCode.valueOf(pmos.getErrcode()));
    }

    @PutMapping("/open-close-odds")
    public ResponseResult updateUserOddsStatus(@RequestBody OddsCloseRQ oc){
        UserLogin user=getLoginExt();
        oc.setUserId(user.getPrimaryId());
        oc.setUserLevel(user.getUserLevel());
        Boolean success=userOddsService.updateUserOddsStatus(oc);
        return success?ResponseData.SUCCESS(null):ResponseData.FAIL();
    }

    @GetMapping("/now-lottery")
    public ResponseData getNowLottery(Integer userId,Integer lotteryId,String market) {
        Integer loginUserId=getPrimaryId();
        NowOrderVO nowOrderVO= nowOrderService.getNowLotteryVO(userId==null?loginUserId:userId,lotteryId,market);
        return ResponseData.SUCCESS(nowOrderVO);
    }

    @GetMapping("/now-stats")
    public ResponseData getNowStats(Integer userId,Integer lotteryId,String gameNo,String buhuo,String markets) {
        Integer loginUserId=getPrimaryId();
        NowOrderVO nowOrderVO= nowOrderService.getNowStats(userId==null?loginUserId:userId,lotteryId,gameNo,buhuo,markets);
        return ResponseData.SUCCESS(nowOrderVO);
    }
    @GetMapping("/now-odds")
    public ResponseData getNowOdds(Integer userId,Integer lotteryId,String gameNo,String buhuo,String market,String markets) {
        Integer loginUserId=getPrimaryId();
        NowOrderVO nowOrderVO= nowOrderService.getNowOdds(userId==null?loginUserId:userId,lotteryId,gameNo,buhuo,market,markets);
        return ResponseData.SUCCESS(nowOrderVO);
    }

    @GetMapping("/buhuo-amt")
    public ResponseData getBuhuoAmt(ManualBuhuoRQ buhuo) {
        buhuo.setUserId(getPrimaryId());
        Integer amt=nowOrderService.getManualBuhuoAmt(buhuo);
        return ResponseData.SUCCESS(amt);
    }
    @PutMapping("/buhuo-manual")
    public ResponseResult saveBuhuoManual(@RequestBody ManualBuhuoRQ buhuo) {
        buhuo.setUserId(getPrimaryId());
        nowOrderService.manualBuhuo(buhuo);
        return  ResponseData.SUCCESS();
    }
}
