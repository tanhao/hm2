package com.xzy.ctrl;

import java.util.List;
import java.util.Map;

import com.xzy.pojo.ctrl.CategoryDO;
import com.xzy.pojo.ctrl.KindDO;

public interface CategoryService {

    void saveCategoryToRedis();
    void delCategoryForRedis();

    Map<Integer, CategoryDO> mapGategorys();

    Map<Integer, List<CategoryDO>> mapCategorysOfKindId();

    Map<Integer,CategoryDO> mapGategorys(Map<Integer, KindDO> mapKind) ;

}