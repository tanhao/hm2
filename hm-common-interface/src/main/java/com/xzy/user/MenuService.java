package com.xzy.user;

import com.xzy.pojo.user.HmMenu;
import com.xzy.pojo.user.HmRoleMenu;

import java.util.List;

public interface MenuService{
    public List<HmMenu> selectByParentId(Integer parentId);

    public void add(HmMenu menu);

    public void update(HmMenu user);

    public void delete(Integer menuId);

    public List<HmMenu> selMenuList();

    /**
     * 查询 HM_ROLE_MENU 表数据
     * @return
     */
    public List<HmRoleMenu> selRoleMenuList(HmRoleMenu roleMenu);

    public void addRoleMenu(HmRoleMenu roleMenu);

    public void delRoleMenu(HmRoleMenu roleMenu);

    List<HmMenu> selectByRoleLevel(Integer level);

    List<HmMenu> selectBySonId(Integer sonId);

}
