package com.xzy.ctrl.service.impl;

import com.xzy.ctrl.CategoryService;
import com.xzy.ctrl.dao.CategoryMapper;
import com.xzy.pojo.ctrl.CategoryDO;
import com.xzy.pojo.ctrl.KindDO;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 补货业务逻辑层
 */
@DubboService
@SuppressWarnings("all")
public class CategoryServiceImpl implements CategoryService {

    private final String CATEGORY = "category";

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void saveCategoryToRedis() {
        if (!redisTemplate.hasKey(CATEGORY)) {
            List<CategoryDO> listCategorys = categoryMapper.listCategorys();
            listCategorys.forEach(category -> redisTemplate.boundHashOps(CATEGORY)
                    .put(category.getCategoryId().toString(), category));
        }
    }
    @Override
    public void delCategoryForRedis() {
        redisTemplate.delete(CATEGORY);
    }

    @Override
    public Map<Integer, CategoryDO> mapGategorys() {
        List<CategoryDO> categoryDOs = redisTemplate.boundHashOps(CATEGORY).values();
        return categoryDOs.stream().collect(Collectors.toMap(CategoryDO::getCategoryId, Function.identity()));
    }

    @Override
    public Map<Integer, List<CategoryDO>> mapCategorysOfKindId() {
        List<CategoryDO> categoryDOs = redisTemplate.boundHashOps(CATEGORY).values();
        Map<Integer, List<CategoryDO>> categrorys = categoryDOs.stream()
                .collect(Collectors.groupingBy(CategoryDO::getKindId));
        for (List<CategoryDO> item : categrorys.values()) {
            item.sort((a1, a2) -> a1.getOrdering().compareTo(a2.getOrdering()));
        }
        return categrorys;

    }

    @Override
    public Map<Integer,CategoryDO> mapGategorys(Map<Integer, KindDO> mapKind) {
        List<CategoryDO> categoryDOs = redisTemplate.boundHashOps(CATEGORY).values();
        return categoryDOs.stream().filter(category->mapKind.containsKey(category.getKindId())).collect(Collectors.toMap(CategoryDO::getCategoryId, Function.identity()));
    }

}