package com.xzy.order.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.xzy.common.exception.ExceptionCast;
import com.xzy.common.model.code.CommonCode;
import com.xzy.enums.ResultStatusEnum;
import com.xzy.order.OrderService;
import com.xzy.order.dao.OrderMapper;
import com.xzy.pojo.order.HmOrder;
import com.xzy.pojo.order.request.OrderAgRq;
import com.xzy.pojo.order.request.OrderMemRq;
import com.xzy.pojo.order.request.WinLoseRq;
import com.xzy.pojo.order.response.BhAmtRp;
import com.xzy.pojo.order.response.DetailTimeCount;
import com.xzy.pojo.order.response.ReportRp;
import com.xzy.pojo.order.response.WinLoseInfo;
import com.xzy.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@DubboService(interfaceClass = OrderService.class)
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<HmOrder> findByMember(OrderMemRq search) {
        search.setSize(search.getSize() > 1000 ? 1000 : search.getSize());
        PageHelper.startPage(search.getPage(), search.getSize());
        if (search.getUserId() == null && search.getAgentId() == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        return orderMapper.findByMember(search);
    }

    public List<Long> findGameNo(Integer lotteryId, String day) {
        return orderMapper.findGameNo(lotteryId, day);
    }

    public List<HmOrder> findOrderByAgentId(OrderAgRq orderAgRq) {
        orderAgRq.setEndTime(orderAgRq.getStartTime());
        orderAgRq.setLastMonth(DateUtil.checkLastMonth(orderAgRq.getStartTime()));
        if(orderAgRq.getLastMonth()) {
            //上个月的数据去查询备份数据库
            DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("_yyyyMMdd");
            orderAgRq.setDay(LocalDate.parse(orderAgRq.getStartTime()).format(yyyyMMdd));
        }
        if(ObjectUtil.isNull(orderAgRq.getGameNo())){
            PageHelper.startPage(orderAgRq.getPage(), orderAgRq.getSize(),false);
        }else{
            PageHelper.startPage(orderAgRq.getPage(), orderAgRq.getSize());
        }
        return orderMapper.findOrderByAgentId(orderAgRq);
    }

    public HmOrder findOrderByOrderId(String orderId, Integer agentId) {
        return orderMapper.findOrderByOrderId(orderId, agentId);
    }

    public List<WinLoseInfo> selectWinLose(WinLoseRq winLoseRq) {
        if (StrUtil.isBlank(winLoseRq.getStatus())) {
            winLoseRq.setStatus(ResultStatusEnum.DIVIDEND.toString());
        }
        if (winLoseRq.getStatus().equals(ResultStatusEnum.DIVIDEND.toString())) {
            return orderMapper.selectWinLose(winLoseRq);
        }else if(ResultStatusEnum.VOID.toString().equals(winLoseRq.getStatus())){
            return orderMapper.selectWinLoseVoid(winLoseRq);
        }
        return orderMapper.selectWinLoseWaiting(winLoseRq);
    }

    public BhAmtRp selectBhWinLose(WinLoseRq winLoseRq) {
        if (StrUtil.isBlank(winLoseRq.getStatus())) {
            winLoseRq.setStatus("DIVIDEND");
        }
        if (winLoseRq.getStatus().equals("DIVIDEND")) {
            return orderMapper.selectBhWinLose(winLoseRq);
        }
        return orderMapper.selectBhWinLoseWaiting(winLoseRq);
    }

    public List<WinLoseInfo> selectWinLoseByLottery(WinLoseRq winLoseRq) {
        if (StrUtil.isBlank(winLoseRq.getStatus())) {
            winLoseRq.setStatus(ResultStatusEnum.DIVIDEND.toString());
        }
        if (winLoseRq.getStatus().equals(ResultStatusEnum.DIVIDEND.toString())) {
            return orderMapper.selectWinLoseByLottery(winLoseRq);
        }else if(ResultStatusEnum.VOID.toString().equals(winLoseRq.getStatus())){
            return orderMapper.selectWinLoseByLotteryVoid(winLoseRq);
        }
        return orderMapper.selectWinLoseByLotteryWaiting(winLoseRq);
    }

    public List<WinLoseInfo> selectBhWinLoseByLottery(WinLoseRq winLoseRq) {
        if (StrUtil.isBlank(winLoseRq.getStatus())) {
            winLoseRq.setStatus("DIVIDEND");
        }
        if (winLoseRq.getStatus().equals("DIVIDEND")) {
            return orderMapper.selectBhWinLoseByLottery(winLoseRq);
        }
        return orderMapper.selectBhWinLoseByLotteryWaiting(winLoseRq);
    }

    public List<WinLoseInfo> selectKind(WinLoseRq winLoseRq) {
        if (StrUtil.isBlank(winLoseRq.getStatus())) {
            winLoseRq.setStatus("DIVIDEND");
        }
        if (winLoseRq.getStatus().equals("DIVIDEND")) {
            return orderMapper.selectKind(winLoseRq);
        }else if(ResultStatusEnum.VOID.toString().equals(winLoseRq.getStatus())){
            return orderMapper.selectKindVoid(winLoseRq);
        }
        return orderMapper.selectKindWaiting(winLoseRq);
    }

    /**
     * 取两周报表
     */
    public JSONObject getTwoWeeksReport(Integer userId, Integer lotteryId, String winOrLoserState) {
        LocalDate thisMonday = DateUtil.memberWeekGetMonday();
        LocalDate prevMonday = thisMonday.minusWeeks(1);
        LocalTime localTime = LocalTime.now();
        LocalDate nextMonday = null;
        String[] timeArr = localTime.toString().split(":");
        if (Integer.parseInt(timeArr[0]) < 7) {
            nextMonday = thisMonday.plusDays(-1).plusWeeks(1);
        } else {
            nextMonday = thisMonday.plusWeeks(1);
        }
        List<ReportRp> list = null;
        if (null == winOrLoserState || (winOrLoserState.equals("DIVIDEND") && null == lotteryId)) {
            list = orderMapper.getDayReportsTwo(userId, prevMonday);
        } else {
            list = orderMapper.getDayReports(userId, prevMonday, lotteryId, winOrLoserState);
        }

        JSONArray prev = new JSONArray();
        JSONArray now = new JSONArray();
        LocalDate last = prevMonday;
        //需要补齐前面没有数据的日期
        if (list.size() == 0 || list.get(0).getAccountDay().isAfter(last)) {
            int diffDays = DateUtil.getDayDiff(prevMonday, list.size() == 0 ? thisMonday : list.get(0).getAccountDay());
            for (int d = 0; d < diffDays; d++) {
                LocalDate actDay = prevMonday.plusDays(d);
                JSONObject dayObj = createReportJson(new ReportRp(actDay), 1);
                if (actDay.isBefore(thisMonday)) {
                    prev.add(dayObj);
                } else {
                    now.add(dayObj);
                }
                last = actDay;
            }
        }
        for (ReportRp reportRp : list) {
            if (Integer.parseInt(timeArr[0]) < 7 && now.size() == 7) {
                break;
            }
            JSONObject dayObj = createReportJson(reportRp, 1);
            //中间数据也要补
            if (last.isBefore(reportRp.getAccountDay().minusDays(1))) {
                int diffDays = DateUtil.getDayDiff(last, reportRp.getAccountDay().minusDays(1));
                for (int d = 1; d <= diffDays; d++) {
                    LocalDate actDay = last.plusDays(d);
                    JSONObject midDayObj = createReportJson(new ReportRp(actDay), 1);
                    if (actDay.isBefore(thisMonday)) {
                        prev.add(midDayObj);
                    } else {
                        now.add(midDayObj);
                    }
                }
            }
            if (reportRp.getAccountDay().isBefore(thisMonday)) {
                prev.add(dayObj);
                last = reportRp.getAccountDay();
            } else {
                now.add(dayObj);
                last = reportRp.getAccountDay();
            }

        }
        //需要补齐后面没有数据的日期
        if (last.isBefore(nextMonday)) {
            int diffDays = DateUtil.getDayDiff(last, nextMonday);
            for (int d = 1; d < diffDays; d++) {
                LocalDate actDay = last.plusDays(d);
                JSONObject dayObj = createReportJson(new ReportRp(actDay), 1);
                if (actDay.isBefore(thisMonday)) {
                    prev.add(dayObj);
                } else {
                    now.add(dayObj);
                }
            }
        }
        JSONObject report = new JSONObject();
        report.put("now", now);
        report.put("prev", prev);
        return report;
    }

    /**
     * 生成报表JSON数据
     */
    private JSONObject createReportJson(ReportRp reportRp, Integer type) {
        JSONObject dayObj = new JSONObject();
        if (type == 1) {
            dayObj.put("date", reportRp.getAccountDay().format(DateTimeFormatter.ofPattern(DateUtil.ACCOUNT_DAY_PATTERN)));
            dayObj.put("week", reportRp.getAccountDay().getDayOfWeek());
        } else {
            dayObj.put("lotteryId", reportRp.getLotteryId());
            dayObj.put("lotteryKey", reportRp.getLotteryKey());
        }
        dayObj.put("num", reportRp.getTicketNum());
        dayObj.put("betAmt", reportRp.getBetAmt().setScale(2, BigDecimal.ROUND_HALF_UP));
        dayObj.put("actAmt", reportRp.getActAmt().setScale(2, BigDecimal.ROUND_HALF_UP));
        dayObj.put("comm", reportRp.getComm().setScale(2, BigDecimal.ROUND_HALF_UP));
        if (reportRp.getWinAmt() == null) {
            reportRp.setWinAmt(new BigDecimal("0"));
        }
        dayObj.put("winAmt", reportRp.getWinAmt().setScale(2, BigDecimal.ROUND_HALF_UP));
        return dayObj;
    }

    public JSONArray getLotteryReport(Integer userId, String localDate, String winOrLoserState) {
        JSONArray report = new JSONArray();
        List<ReportRp> list = orderMapper.getLotteryReports(userId, localDate, winOrLoserState);
        for (ReportRp reportRp : list) {
            report.add(createReportJson(reportRp, 2));
        }
        return report;
    }

    /**
     * 作废注单
     */
    public void voidOrderByOrderId(List<String> orderIds, String voidReason) {
        orderIds.forEach(orderId -> {
            HmOrder hmOrder = orderMapper.selectByPrimaryKey(orderId);
            LocalDate day = hmOrder.getAccountDay().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (DateUtil.getDayDiff(day, DateUtil.getAccountDay()) > 7) {
                ExceptionCast.cast("超过7天不能取消注单！");
            }
            if (hmOrder != null && hmOrder.getStatus().equals(ResultStatusEnum.DIVIDEND.toString())) {
                HmOrder upOrder = new HmOrder();
                upOrder.setStatus(ResultStatusEnum.VOID.toString());
                upOrder.setOrderId(orderId);
                upOrder.setVoidReason(voidReason);
                //orderMapper.updateByPrimaryKeySelective(upOrder);
            }
        });
    }

    /**
     * 取消当期
     */
    public synchronized void voidOrderByUser(Integer lotteryId, Long gameNo, String username, String voidReason, String voidType) {
        Date date = orderMapper.selectAccountByGameNo(lotteryId, gameNo);
        if (date == null) {
            ExceptionCast.cast("未查询到当期注单！");
        }
        LocalDate day = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (DateUtil.getDayDiff(day, DateUtil.getAccountDay()) > 7) {
            ExceptionCast.cast("超过7天不能取消注单！");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("lotteryId", lotteryId);
        map.put("gameNo", gameNo);
        map.put("voidType", voidType);
        map.put("voidReason", voidReason);
        try {
            orderMapper.voidOrderByUser(map);
        } catch (Exception e) {
            log.info(map.toString());
            log.info(e.getMessage());
            ExceptionCast.cast("作废失败！");
        }
    }

    public List<DetailTimeCount> selectDetailTimeCount(WinLoseRq search) {
        if (search.getStatus().equals(ResultStatusEnum.DIVIDEND.toString())) {
            return orderMapper.selectDetailTimeCount(search);
        }else if (search.getStatus().equals(ResultStatusEnum.VOID.toString())){
            return orderMapper.selectDetailTimeCountVoid(search);
        }
        return orderMapper.selectDetailTimeCountWaiting(search);
    }
}
