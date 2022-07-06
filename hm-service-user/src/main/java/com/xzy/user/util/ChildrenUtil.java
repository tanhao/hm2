package com.xzy.user.util;

import cn.hutool.core.util.ObjectUtil;
import com.xzy.pojo.user.HmMenu;

import java.util.ArrayList;
import java.util.List;

public class ChildrenUtil {

    public static List<HmMenu> childrenMenuList(List<HmMenu> hmMenus, Integer pid) {
        if (hmMenus.size() == 0) {
            return hmMenus;
        }
        List<HmMenu> rootMenus = new ArrayList<>();
        for (HmMenu menu : hmMenus) {
            if (ObjectUtil.equal(menu.getParentId(), pid)) {
                menu.setChildren(childrenMenuList(hmMenus, menu.getMenuId()));
                rootMenus.add(menu);
            }
        }
        return rootMenus;
    }
}
