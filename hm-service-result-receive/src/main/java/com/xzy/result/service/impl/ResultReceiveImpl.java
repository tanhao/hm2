package com.xzy.result.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CommonCode;
import com.xzy.common.model.code.HmResultCode;
import com.xzy.enums.ResultStatusEnum;
import com.xzy.pojo.result.HmResult;
import com.xzy.pojo.result.HmResultLog;
import com.xzy.pojo.result.ext.ResultGrab;
import com.xzy.pojo.result.request.ResultRq;
import com.xzy.pojo.result.vo.ResultCountVo;
import com.xzy.pojo.tio.entity.SendInfo;
import com.xzy.pojo.tio.utils.CodeUtil;
import com.xzy.result.ResultLogService;
import com.xzy.result.ResultReceiveService;
import com.xzy.result.dao.HmResultMapper;
import com.xzy.socket.TioSocketService;
import com.xzy.utils.CodeUtils;
import com.xzy.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@DubboService(interfaceClass = ResultReceiveService.class)
@Transactional
@Slf4j
public class ResultReceiveImpl implements ResultReceiveService {
    @Autowired
    HmResultMapper hmResultMapper;
    @DubboReference
    TioSocketService tioSocketService;
    @DubboReference
    ResultLogService resultLogService;

    public void saveResultLog(String type, Integer resultId, Integer loginId, String user, String body, String data) {
        try {
            HmResultLog hmResultLog = new HmResultLog();
            hmResultLog.setCreateDay(new Date());
            hmResultLog.setCreateTime(new Date());
            hmResultLog.setCreateType(type);
            hmResultLog.setCreateId(loginId);
            hmResultLog.setCreateUser(user);
            hmResultLog.setResultId(resultId);
            hmResultLog.setRequestBody(body);
            hmResultLog.setResponseData(data);
            resultLogService.saveResultLog(hmResultLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<HmResult> findListByLotteryIdDay(ResultRq params) {
        PageHelper.startPage(params.getPage(), params.getSize());
        Example example = new Example(HmResult.class);
        Example.Criteria criteria = example.createCriteria();
        if (params.getAccountDay() == null) {
            params.setAccountDay(DateUtil.getAccountDay().toString());
        }
        criteria.andEqualTo("accountDay", params.getAccountDay());
        criteria.andEqualTo("lotteryId", params.getLotteryId());
        criteria.andLessThan("actionTime", Instant.now().toEpochMilli() / 1000);
        if (params.getGameNo() != null) {
            criteria.andEqualTo("gameNo", params.getGameNo());
        }
        example.setOrderByClause("game_no desc");
        return hmResultMapper.selectByExample(example);
    }

    /**
     * 推送信息
     *
     * @param resultGrab
     */
    public void sendResult(ResultGrab resultGrab) {
        try {
            long st = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            JSONObject object = new JSONObject();
            object.put("gameNo", resultGrab.getGameNo());
            object.put("result", resultGrab.getResult());
            object.put("lotteryKey", resultGrab.getLotteryKey());
            object.put("stats", resultGrab.getStats());
            SendInfo sendInfo = new SendInfo(CodeUtil.MSG_RESULT, object);
            tioSocketService.sendToGroup("L" + resultGrab.getLotteryKey(), sendInfo);
            long et = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            log.info("推送[{}][{}]状态{}，时间[{}]，来源[{}]", resultGrab.getLotteryKey(), resultGrab.getGameNo(), resultGrab.getErrcode(), (et - st), resultGrab.getUpdateBy());
            JSONArray jsonArray = JSONArray.parseArray(resultGrab.getCljp());
            if (jsonArray == null) {
                return;
            }
            for (Iterator<Object> iterator = jsonArray.iterator(); iterator.hasNext(); ) {
                JSONObject cljp = (JSONObject) iterator.next();
                String lotteryId = cljp.getString("lotteryId");
                String userId = cljp.getString("userId");
                String groupName = StrUtil.format("{}-{}", lotteryId, userId);
                cljp.remove("lotteryId");
                cljp.remove("userId");
                SendInfo sendInfo2 = new SendInfo("odds_cljp", cljp);
                log.info("推送赛果[{}:::{}]", groupName, sendInfo2.getContent());
                tioSocketService.sendToGroup(groupName, sendInfo2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 输入赛果派彩
     *
     * @param resultGrab
     */
    public synchronized Integer saveResult(ResultGrab resultGrab) {
        long st = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        hmResultMapper.saveResult(resultGrab);
        if (resultGrab.getErrcode().equals(200)) {
            long et = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            log.info("保存[{}][{}]状态{}，时间[{}]，来源[{}]", resultGrab.getLotteryKey(), resultGrab.getGameNo(), resultGrab.getErrcode(), (et - st), resultGrab.getUpdateBy());
        }
        return resultGrab.getErrcode();
    }

    /**
     * 手动派彩
     */
    public void savePayoutResult(Integer resultId, Integer loginId, String updateBy) {
        if (ObjectUtil.isNull(resultId)) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        HmResult hmResult = hmResultMapper.selectByPrimaryKey(resultId);
        //只有未完成的赛果才能进行派彩操作
        if (hmResult != null && hmResult.getStatus().equals(ResultStatusEnum.WAITING.toString())) {
            ResultGrab resultGrab = new ResultGrab();
            resultGrab.setLotteryKey(hmResult.getLotteryKey());
            resultGrab.setGameNo(hmResult.getGameNo());
            resultGrab.setResult(hmResult.getResult());
            resultGrab.setUpdateBy(updateBy);
            Integer i = spPayoutPrize(resultGrab);
            if (!i.equals(200)) {
                log.info("派彩失败信息:" + i);
                ExceptionCast.cast("派彩失败,请刷新重试！");
            } else {

            }
            this.saveResultLog("期号派彩", resultId, loginId, updateBy, "", i.toString());
        } else {
            ExceptionCast.cast("结果已经派彩，请勿重复操作！");
        }
    }

    /**
     * 重新派彩
     *
     * @param resultId
     * @param result
     * @param updateBy
     */
    public void resultRePayout(Integer resultId, String result, String updateBy, Integer loginId) {
        HmResult hmResult = hmResultMapper.selectByPrimaryKey(resultId);
        if (hmResult != null) {
            if (hmResult.getAccountDay().isBefore(DateUtil.getThisWeekMonday()) || hmResult.getAccountDay().isAfter(DateUtil.getThisWeekSunday())) {
                ExceptionCast.cast(HmResultCode.NO_RESULT_DATE);
            }
        }
        Map<String, Object> maps = new HashMap<>();
        maps.put("resultId", resultId);
        maps.put("result", result);
        maps.put("userName", updateBy);
        maps.put("errcode", 100);
        hmResultMapper.spPayoutPrizeRepeat(maps);
        Object errcode = maps.get("errcode");
        if (!errcode.equals(200)) {
            ExceptionCast.cast(CodeUtils.getByCode(HmResultCode.class, (int) maps.get("errcode")));
        }
        String body = hmResult.getResult() + "=>" + result;
        this.saveResultLog("重新派彩", resultId, loginId, updateBy, body, errcode.toString());
    }

    /**
     * 撤单
     *
     * @param resultId
     * @param updateBy
     */
    public void resultCancel(Integer resultId, String updateBy, Integer loginId) {
        HmResult hmResult = hmResultMapper.selectByPrimaryKey(resultId);
        //只有未完成的赛果才能撤单
        if (hmResult != null && hmResult.getStatus().equals(ResultStatusEnum.WAITING.toString()) && hmResult.getResult() == null) {
            //取消所有注单
            hmResultMapper.cancelOrder(hmResult.getLotteryId(), hmResult.getGameNo());
            HmResult upResult = new HmResult();
            upResult.setId(resultId);
            upResult.setStatus(ResultStatusEnum.CANCEL.toString());
            upResult.setUpdateBy(updateBy);
            int i = hmResultMapper.updateByPrimaryKeySelective(upResult);
            this.saveResultLog("撤单", resultId, loginId, updateBy, "", "");
        } else {
            ExceptionCast.cast("撤单失败！");
        }
    }


    @Override
    public List<HmResult> findTotalResult(String accountDay) {
        if (StrUtil.isBlank(accountDay)) {
            accountDay = DateUtil.getAccountDay().toString();
        }
        return hmResultMapper.findTotalResult(accountDay);
    }

    public List<ResultCountVo> selectCountByResultDay(String day) {
        return hmResultMapper.selectCountByResultDay(day);
    }

    public List<ResultCountVo> selectCountByAccountDay(String day) {
        return hmResultMapper.selectCountByAccountDay(day);
    }

    public List<HmResult> selectNoResultByLottery(Integer lotteryId, String day) {
        return hmResultMapper.selectNoResultByLottery(lotteryId, day);
    }

    public List<HmResult> selectNoAccountByLottery(Integer lotteryId, String day) {
        return hmResultMapper.selectNoAccountByLottery(lotteryId, day);
    }

    public Integer spPayoutPrize(ResultGrab resultGrab) {
        long st = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Map<String, Object> maps = new HashMap<>();
        maps.put("lotteryKey", resultGrab.getLotteryKey());
        maps.put("gameNo", resultGrab.getGameNo());
        maps.put("result", resultGrab.getResult());
        maps.put("userName", resultGrab.getUpdateBy());
        maps.put("errcode", 100);
        maps.put("lotteryInfo", "");
        hmResultMapper.spPayoutPrize(maps);
        long et = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        log.info("派彩[{}][{}]状态{}，时间[{}]，来源[{}]", resultGrab.getLotteryKey(), resultGrab.getGameNo(), maps.get("errcode"), (et - st), resultGrab.getUpdateBy());
        try {
            if (maps.get("errcode").equals(200)) {
                log.info(maps.get("lotteryInfo").toString());
                hmResultMapper.spBackOrder(maps.get("lotteryInfo").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (Integer) maps.get("errcode");
    }


    public void addResult(List<HmResult> results) {
        hmResultMapper.addResult(results);
    }

}
