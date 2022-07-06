package com.xzy.user.dao;

import com.xzy.pojo.user.HmMenuSon;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MenuSonMapper extends Mapper<HmMenuSon> {
    @Select("SELECT son.menu_id FROM hm_menu_son son,hm_menu hm WHERE hm.menu_id=son.menu_id AND son.son_id = #{sonId}  AND hm.mtype=2")
    List<Integer> selectMenuBySonNoParent(Integer sonId);

    @Delete("DELETE FROM hm_menu_son WHERE  son_id IN " +
            "(SELECT son_id FROM hm_user_son WHERE primary_id IN " +
            "(SELECT user_id FROM hm_user WHERE user_level = " +
            "(SELECT user_level FROM hm_role WHERE role_id = #{roleId}) ) ) " +
            "AND menu_id NOT IN " +
            "(SELECT menu_id FROM  hm_role_menu WHERE role_id = #{roleId} ) ")
    Integer deleteByRoleId(Integer roleId);

}
