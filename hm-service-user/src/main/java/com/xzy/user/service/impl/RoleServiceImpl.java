package com.xzy.user.service.impl;

import com.xzy.pojo.user.HmRole;
import com.xzy.user.RoleService;
import com.xzy.user.dao.RoleMapper;
import com.xzy.user.dao.RoleMenuMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@DubboService
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询所有的角色
     *
     * @return
     */
    public List<HmRole> getListRole() {
        return roleMapper.selectAll();
    }

    /**
     * 新增
     *
     * @param role
     */
    public void add(HmRole role) {
        roleMapper.insert(role);
    }

    /**
     * 修改
     *
     * @param role
     */
    public void update(HmRole role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }

    /**
     * 删除角色和当前角色的权限
     *
     * @param roleId
     */
    public void delete(Integer roleId) {
        roleMapper.deleteByPrimaryKey(roleId);
        roleMenuMapper.deleteByPrimaryKey(roleId);
    }

}
