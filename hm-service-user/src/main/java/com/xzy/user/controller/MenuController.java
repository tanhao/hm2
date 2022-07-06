package com.xzy.user.controller;

import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.controller.BaseController;
import com.xzy.pojo.user.HmMenu;
import com.xzy.pojo.user.HmRoleMenu;
import com.xzy.user.MenuService;
import com.xzy.user.util.ChildrenUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("menu")
@CrossOrigin
public class MenuController extends BaseController {

    @DubboReference
    private MenuService menuService;


    /**
     * 查询菜单 根据父级获取菜单栏 0代表最上级菜单
     */
    @GetMapping("/menuList/{pId}")
    public ResponseResult getMenuList(@PathVariable("pId") Integer pId) {
        return ResponseData.SUCCESS("menus", menuService.selectByParentId(pId));
    }

    /**
     * 查询所有菜单
     */
    @GetMapping("tree/{roleId}")
    public ResponseResult getMenuTree(@PathVariable("roleId") Integer roleId) {
        Map<String, Object> map = new HashMap<>();
        List<HmMenu> hmMenus = menuService.selMenuList();
        if (roleId != 0) {
            HmRoleMenu roleMenu = new HmRoleMenu();
            roleMenu.setRoleId(roleId);
            List<HmRoleMenu> selRoleMenu = menuService.selRoleMenuList(roleMenu);
            map.put("menuIds", selRoleMenu.stream().map(HmRoleMenu::getMenuId).collect(Collectors.toList()));
        }
        map.put("hmMenus", hmMenus);
        map.put("routers", ChildrenUtil.childrenMenuList(hmMenus, 0));
        return ResponseData.SUCCESS(map);
    }

    /**
     * 添加角色和权限
     *
     * @param role
     * @return
     */
    @PostMapping("/addRoleMenu")
    public ResponseResult addRoleMenu(@RequestBody HmRoleMenu role) {
        if (getLoginExt().getAdmin()) {
            menuService.addRoleMenu(role);
        }
        return ResponseData.SUCCESS();
    }

    /**
     * 添加角色和权限
     *
     * @param role
     * @return
     */
    @DeleteMapping("/delRoleMenu")
    public ResponseResult delRoleMenu(@RequestBody HmRoleMenu role) {
        //menuService.delRoleMenu(role);
        return ResponseData.SUCCESS();
    }

    /**
     * 添加菜单
     */
    @PostMapping("/add")
    public ResponseResult add(@RequestBody HmMenu menu) {
        if (getLoginExt().getAdmin()) {
            menuService.add(menu);
        }
        return ResponseData.SUCCESS();
    }

    /**
     * 修改菜单
     */
    @PostMapping("/update")
    public ResponseResult update(@RequestBody HmMenu menu) {
        if (getLoginExt().getAdmin()) {
            menuService.update(menu);
        }
        return ResponseData.SUCCESS();
    }

    /**
     * 删除菜单
     */
    @GetMapping("/delete/{menuId}")
    public ResponseResult delete(@PathVariable("menuId") Integer menuId) {
        if (getLoginExt().getAdmin()) {
            menuService.delete(menuId);
        }
        return ResponseData.SUCCESS();
    }


}
