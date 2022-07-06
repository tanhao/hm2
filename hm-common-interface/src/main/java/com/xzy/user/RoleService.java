package com.xzy.user;

import com.xzy.pojo.user.HmRole;

import java.util.List;

public interface RoleService {

    public List<HmRole> getListRole();

    public void add(HmRole role);

    public void update(HmRole role);

    public void delete(Integer roleId);

}
