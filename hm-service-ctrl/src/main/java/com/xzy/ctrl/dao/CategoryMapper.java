package com.xzy.ctrl.dao;

import java.util.List;

import com.xzy.pojo.ctrl.CategoryDO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
    List<CategoryDO> listCategorys();
    List<CategoryDO> listCategorysByLotteryId(Integer lotteryId);
}
