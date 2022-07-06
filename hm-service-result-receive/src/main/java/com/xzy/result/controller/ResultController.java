package com.xzy.result.controller;

import com.github.pagehelper.PageInfo;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CommonCode;
import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.controller.BaseController;
import com.xzy.pojo.result.HmResult;
import com.xzy.pojo.result.ext.ResultGrab;
import com.xzy.pojo.result.request.ResultRq;
import com.xzy.pojo.result.response.ResultListRp;
import com.xzy.pojo.result.vo.ResultCountVo;
import com.xzy.pojo.user.ext.UserLogin;
import com.xzy.result.ResultReceiveService;
import com.xzy.result.client.HmResultApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class ResultController extends BaseController {
    @DubboReference
    ResultReceiveService resultReceiveService;
    @Autowired
    HmResultApi hmResultApi;

    /**
     * 代理查询期号数据
     * @param params
     * @return
     */
    @GetMapping("/list")
    public ResponseResult findList(ResultRq params) {
        return ResponseData.SUCCESS_LIST(resultReceiveService.findListByLotteryIdDay(params));
    }

    /**
     * 会员查询期号数据
     * @param params
     * @return
     */
    @PostMapping("/mem/list")
    public ResponseResult findMemList(@RequestBody ResultRq params) {
        List<HmResult> idDay = resultReceiveService.findListByLotteryIdDay(params);
        List<ResultListRp> rp = new ArrayList<>();
        idDay.forEach(re -> {
            rp.add(new ResultListRp(re.getLotteryId(), re.getLotteryKey(), re.getGameNo(), re.getResult(), re.getOpenTime(), re.getActionTime()));
        });
        long total = new PageInfo<>(idDay).getTotal();
        Map<String, Object> map = new HashMap();
        map.put("dataList", rp);
        map.put("total", total);
        return ResponseData.SUCCESS(map);
    }

    @GetMapping("/search/{lotteryKey}/{gameNo}")
    public ResponseResult searchPeriod(@PathVariable String lotteryKey, @PathVariable String gameNo) {
        return ResponseData.SUCCESS(hmResultApi.findPeriod(lotteryKey, gameNo));
    }

    @GetMapping("/search/{lotteryKey}")
    public ResponseResult searchPeriods(@PathVariable String lotteryKey) {
        hmResultApi.findPeriods(lotteryKey, "2020-07-27");
        return ResponseData.SUCCESS();
    }

    @GetMapping("/total/{day}")
    public ResponseResult getTotalResult(@PathVariable String day) {
        return ResponseData.SUCCESS(resultReceiveService.findTotalResult(day));
    }

    @GetMapping("/count/{day}")
    public ResponseResult getTotalCount(@PathVariable String day) {
        List<ResultCountVo> vos = resultReceiveService.selectCountByAccountDay(day);
        return ResponseData.SUCCESS(vos);
    }

    @GetMapping("/null/list/{lotteryId}/{day}")
    public ResponseResult getTotalNullList(@PathVariable Integer lotteryId, @PathVariable String day) {
        List<HmResult> results = resultReceiveService.selectNoAccountByLottery(lotteryId, day);
        return ResponseData.SUCCESS(results);
    }

    /**
     * 手动进行派彩
     * @param resultGrab
     * @return
     */
    @PostMapping("/manual/save")
    public ResponseResult manualSave(@RequestBody ResultGrab resultGrab) {
        if (!getLoginExt().getHouTong()) {
            ExceptionCast.cast(CommonCode.UNAUTHORISE);
        }
        resultGrab.setUpdateBy(getLoginExt().getUsername());
        resultReceiveService.saveResult(resultGrab);
        //如果钩中自动派彩按钮,进行自动派彩
        Integer i = 100;
        if (resultGrab.getAutoPayout()) {
            i = resultReceiveService.spPayoutPrize(resultGrab);
        }
        if (i.equals(200)) {
            resultReceiveService.sendResult(resultGrab);
        } else {
            ExceptionCast.cast("保存赛果失败,请刷新重试！");
        }
        return ResponseData.SUCCESS();
    }

    /**
     * 手动输赛果后没选择派彩 后的重新派彩方法
     *
     * @return
     */
    @PostMapping("/manual/payout/{resultId}")
    public ResponseResult manualPayOut(@PathVariable Integer resultId) {
        UserLogin loginExt = getLoginExt();
        if (!loginExt.getHouTong()) {
            ExceptionCast.cast(CommonCode.UNAUTHORISE);
        }
        resultReceiveService.savePayoutResult(resultId, loginExt.getUserId(), loginExt.getUsername());
        return ResponseData.SUCCESS();
    }

    /**
     * 撤单操作
     *
     * @param resultId
     * @return
     */
    @PostMapping("/cancel/{resultId}")
    public ResponseResult cancel(@PathVariable Integer resultId) {
        UserLogin loginExt = getLoginExt();
        if (!loginExt.getHouTong()) {
            ExceptionCast.cast(CommonCode.UNAUTHORISE);
        }
        resultReceiveService.resultCancel(resultId, loginExt.getUsername(),loginExt.getUserId());
        return ResponseData.SUCCESS();
    }


    /**
     * 重新派彩
     *
     * @param resultGrab
     * @return
     */
    @PostMapping("/rePayout")
    public ResponseResult rePayout(@RequestBody ResultGrab resultGrab) {
        UserLogin loginExt = getLoginExt();
        if (!loginExt.getAdmin()) {
            ExceptionCast.cast(CommonCode.UNAUTHORISE);
        }
        resultReceiveService.resultRePayout(resultGrab.getId(), resultGrab.getResult(), loginExt.getUsername(), loginExt.getUserId());
        return ResponseData.SUCCESS();
    }

}
