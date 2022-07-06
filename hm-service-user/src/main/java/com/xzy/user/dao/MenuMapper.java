package com.xzy.user.dao;

import com.xzy.pojo.user.HmMenu;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MenuMapper extends Mapper<HmMenu> {
    /**
     * 查询菜单
     * @return
     */
    List<HmMenu> selectByParentId(Integer parentId);

    //List<HmMenu> selectByParentId(Integer parentId);

}
