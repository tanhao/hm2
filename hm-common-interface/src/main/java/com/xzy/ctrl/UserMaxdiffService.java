package com.xzy.ctrl;

import com.xzy.pojo.ctrl.request.MaxdiffRQ;
import com.xzy.pojo.ctrl.request.OddsRegressLimitFulloddsRQ;
import com.xzy.pojo.ctrl.vo.MaxdiffVO;

public interface UserMaxdiffService {
    MaxdiffVO getMaxdiffVO(Integer userId);
    MaxdiffRQ updateCompanyMaxdiff(MaxdiffRQ maxdiff);

}