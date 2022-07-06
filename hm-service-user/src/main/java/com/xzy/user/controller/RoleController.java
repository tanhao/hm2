package com.xzy.user.controller;

import com.xzy.common.model.response.ResponseData;
import com.xzy.common.model.response.ResponseResult;
import com.xzy.pojo.user.HmRole;
import com.xzy.user.RoleService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("role")
@CrossOrigin
public class RoleController {

    @DubboReference
    private RoleService roleService;

    /**
     * 查询角色
     *
     * @return
     */
    @GetMapping("/roleList")
    public ResponseResult getListRole() {
        List<HmRole> list = roleService.getListRole();
        return ResponseData.SUCCESS(list);
    }

    /**
     * 添加角色
     */
    @PostMapping("/add")
    public ResponseResult add(@RequestBody HmRole role) {
        roleService.add(role);
        return ResponseData.SUCCESS();
    }

    /**
     * 修改角色
     */
    @PostMapping("/update")
    public ResponseResult update(@RequestBody HmRole role) {
        roleService.update(role);
        return ResponseData.SUCCESS();
    }

    /**
     * 删除角色
     */
    @GetMapping("/delete/{roleId}")
    public ResponseResult delete(@PathVariable("roleId") Integer roleId) {
        System.out.println("DEL>>>>>>>>>>>>>>>>>" + roleId);
        roleService.delete(roleId);
        return ResponseData.SUCCESS();
    }


}
