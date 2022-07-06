package com.xzy.ctrl.service.impl;

import com.xzy.ctrl.CategoryService;
import com.xzy.pojo.ctrl.CategoryDO;
import com.xzy.pojo.ctrl.UserCategoryDO;
import com.xzy.pojo.ctrl.vo.CategoryVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 补货业务逻辑层
 */
@DubboService
@Slf4j
public abstract class UserCategoryServiceImpl extends  UserKindServiceImpl {

    @DubboReference
    private CategoryService categoryService;

    public <T extends CategoryVO> T getCategoryVO(T t, Integer userId) {
        CategoryVO categoryVO=this.getKindVO(t,userId);
        Map<Integer, List<CategoryDO>> mapCategorys = categoryService.mapCategorysOfKindId();
        mapCategorys.values().forEach(kinds -> kinds.forEach(kind -> {
            this.JsonIgnoreCategory(kind);
        }));

        Map<Integer,? extends  Map<Integer,UserCategoryDO>> mapUserCateogorys = this.listUserCategoryDO(userId).stream().collect(Collectors.groupingBy(UserCategoryDO::getLotteryId,Collectors.toMap(item -> item.getCategoryId(), Function.identity())));
        mapUserCateogorys.values().forEach(map->map.values().forEach(item->{
            item.setId(null);
            item.setUserId(null);
            item.setCategoryId(null);
            item.setLotteryId(null);
        }));

        categoryVO.setCategorys(mapCategorys);
        categoryVO.setUserCategorys(mapUserCateogorys);
        return t;
    }

    public abstract <T extends UserCategoryDO> List<T> listUserCategoryDO(Integer userId);

    private void JsonIgnoreCategory(CategoryDO category) {
        category.setCategoryKey(null);
        category.setOdds(null);
        category.setKindId(null);
        category.setOrdering(null);
    }

}
