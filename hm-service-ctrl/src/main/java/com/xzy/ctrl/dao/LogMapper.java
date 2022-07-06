package com.xzy.ctrl.dao;

import com.xzy.pojo.ctrl.OddsDO;
import com.xzy.pojo.ctrl.request.LogRQ;
import com.xzy.pojo.ctrl.response.LogRP;
import com.xzy.pojo.ctrl.vo.LogVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LogMapper {
    List<LogRP> getLogCtrl(LogRQ logRQ);
    List<LogRP> getLogZhuanRegress(LogRQ logRQ);
    List<LogRP> getLogZhuanOdds(LogRQ logRQ);
    List<LogRP> getLogOdds(LogRQ logRQ);
    List<String> listGameNos(LogRQ logRQ);

    Integer getLogCtrlTotal(LogRQ logRQ);
    Integer getLogZhuanRegressTotal(LogRQ logRQ);
    Integer getLogZhuanOddsTotal(LogRQ logRQ);
    Integer getLogOddsTotal(LogRQ logRQ);


    List<LogRP> getLogLimit(LogRQ logRQ);
    Integer getLogLimitTotal(LogRQ logRQ);

}
