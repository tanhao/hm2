package com.xzy.user.service.impl;

import com.xzy.common.exception.ExceptionCast;
import com.xzy.pojo.user.HmMenu;
import com.xzy.pojo.user.HmMenuSon;
import com.xzy.pojo.user.HmRoleMenu;
import com.xzy.user.MenuService;
import com.xzy.user.dao.MenuMapper;
import com.xzy.user.dao.MenuSonMapper;
import com.xzy.user.dao.RoleMenuMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DubboService
public class MenuServiceImpl implements MenuService   {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private MenuSonMapper menuSonMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 根据Id查询
     * @param parentId
     * @return
     */
    public List<HmMenu> selectByParentId(Integer parentId) {
        return menuMapper.selectByParentId(parentId);
    }

    /**
     * 查询所有的菜单
     * @return
     */
    public List<HmMenu> selMenuList(){
        return  menuMapper.selectAll();
    }
    /**
     * 新增
     * @param menu
     */
    public void add(HmMenu menu) {
        menuMapper.insert(menu);
    }
    /**
     * 修改
     * @param menu
     */
    public void update(HmMenu menu) {
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    /**
     *  删除
     * @param menuId
     */
    @Transactional
    public void delete(Integer menuId) {
        if(menuId !=null) {
            List<HmMenu> hmMenus = menuMapper.selectByParentId(menuId);
            if(hmMenus.size()>0){
                ExceptionCast.cast("请先删除下级数据");
            }
            HmRoleMenu hmRoleMenu = new HmRoleMenu();
            hmRoleMenu.setMenuId(menuId);
            roleMenuMapper.delete(hmRoleMenu);
            HmMenuSon hmMenuSon = new HmMenuSon();
            hmMenuSon.setMenuId(menuId);
            menuSonMapper.delete(hmMenuSon);
            menuMapper.deleteByPrimaryKey(menuId);
        }
    }

    /**
     * 根据 roleId 查询 HM_ROLE_MENU 表数据
     * @param roleMenu
     * @return
     */
    public List<HmRoleMenu> selRoleMenuList(HmRoleMenu roleMenu){
        return roleMenuMapper.select(roleMenu);
    }

    /**
     * 添加角色菜单权限
     */
    public void addRoleMenu(HmRoleMenu roleMenu) {
        roleMenuMapper.deleteByPrimaryKey(roleMenu.getRoleId());//删除之前保存的菜单
        addDelRoleMenuList(roleMenu.getRoleId(), roleMenu.getMenuIds());
    }

    /**
     * 删除角色菜单权限
     */
    public void delRoleMenu(HmRoleMenu roleMenu){
        addDelRoleMenuList(roleMenu.getRoleId(), roleMenu.getMenuIds());
    }

    /**
     * 添加或删除 角色菜单权限
     * @param roleId
     * @param menuIds
     */
    public void addDelRoleMenuList(Integer roleId,Integer[] menuIds){
        if (roleId == null || menuIds == null || menuIds.length < 1) {
            return;
        }
        for(Integer menuId :menuIds){
            HmRoleMenu hmRoleMenu = new HmRoleMenu();
            hmRoleMenu.setRoleId(roleId);
            hmRoleMenu.setMenuId(menuId);
            roleMenuMapper.insert(hmRoleMenu);
        }
        menuSonMapper.deleteByRoleId(roleId);
    }

    /**
     * 根据用户等级查询菜单
     * @param level
     * @return
     */
    public List<HmMenu> selectByRoleLevel(Integer level){
        return  roleMenuMapper.selectByRoleLevel(level);
    }


    public List<HmMenu> selectBySonId(Integer sonId){
        return roleMenuMapper.selectBySonId(sonId);
    }
}
