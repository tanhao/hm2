package com.xzy.ctrl;

import com.xzy.pojo.ctrl.request.DiffRegressRQ;
import com.xzy.pojo.ctrl.request.OddsRegressLimitFulloddsRQ;
import com.xzy.pojo.ctrl.vo.ZhuanRegressVO;

public interface UserZhuanRegressService {
    ZhuanRegressVO getZhuanRegressVO(Integer userId);
    OddsRegressLimitFulloddsRQ updateZhuanRegress(OddsRegressLimitFulloddsRQ orlf);
}