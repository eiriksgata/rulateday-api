package com.github.eiriksgata.rulateday.platform.service.rbac;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.Role;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<Role> findAllRolePermission();

    List<Role> selectRoleByUserId(Long userId);

    void roleCreate(Role role);

    void roleUpdate(Role role);

    Role roleQuery(Long roleId);

    List<Role> getRoles();

    void deleteById(Long roleId);
}
