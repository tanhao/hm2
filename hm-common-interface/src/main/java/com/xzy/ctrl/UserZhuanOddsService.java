package com.xzy.ctrl;

import com.xzy.pojo.ctrl.request.DiffOddsRQ;
import com.xzy.pojo.ctrl.request.OddsRegressLimitFulloddsRQ;
import com.xzy.pojo.ctrl.vo.ZhuanOddsVO;

public interface UserZhuanOddsService {
    ZhuanOddsVO getZhuanOddsVO(Integer userId);
    OddsRegressLimitFulloddsRQ updateZhuanOdds(OddsRegressLimitFulloddsRQ orlf);
}