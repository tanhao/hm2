package com.xzy.ctrl;

import com.xzy.pojo.ctrl.CategoryDO;
import com.xzy.pojo.ctrl.OddsDO;
import com.xzy.pojo.ctrl.PlayDO;

import java.util.List;
import java.util.Map;

public interface OddsService {

    void saveOddsToRedis();
    void delOddsForRedis();
    List<OddsDO> listOddss(Map<Integer, PlayDO> mapPlay);

}