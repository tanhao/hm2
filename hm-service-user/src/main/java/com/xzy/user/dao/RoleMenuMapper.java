package com.xzy.user.dao;

import com.xzy.pojo.user.HmMenu;
import com.xzy.pojo.user.HmRoleMenu;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RoleMenuMapper extends Mapper<HmRoleMenu> {

    @Select(" select * from hm_menu where menu_id in ( " +
            "  select menu_id from hm_role_menu where role_id in (" +
            "    select role_id from hm_role where user_level = #{level} and status=1" +
            " )) order by parent_id,sort  ")
    List<HmMenu> selectByRoleLevel(Integer level);

    @Select("SELECT * FROM hm_menu WHERE menu_id IN (SELECT menu_id FROM hm_menu_son WHERE son_id = #{sonId}) order by parent_id,sort ")
    List<HmMenu> selectBySonId(Integer sonId);

}
