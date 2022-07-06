package com.xzy.ctrl;

import com.xzy.pojo.ctrl.request.LogRQ;
import com.xzy.pojo.ctrl.request.ManualBuhuoRQ;
import com.xzy.pojo.ctrl.response.LogRP;
import com.xzy.pojo.ctrl.vo.LogVO;
import com.xzy.pojo.ctrl.vo.NowOrderVO;

import java.util.List;

public interface LogService {
     LogVO getLogVO(Integer userId);
     LogVO getLogCtrl(LogRQ logRQ);
     LogVO getLogZhuanRegress(LogRQ logRQ);
     LogVO getLogZhuanOdds(LogRQ logRQ);
     LogVO getLogOdds(LogRQ logRQ);
     List<String> listGameNos(LogRQ logRQ);

     LogVO getLogLimit(LogRQ logRQ);

}