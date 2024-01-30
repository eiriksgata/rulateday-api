package com.github.eiriksgata.rulateday.platform.service.rbac;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.Role;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<Role> findAllRolePermission();

    List<Role> selectRoleByUserId(Long userId);

    void roleCreate(Role role);

    void roleUpdate(Role role);

    Role roleQuery(Long roleId);

    List<Role> getRoles();

    void deleteById(Long roleId);

    @Transactional(rollbackFor = CommonBaseException.class)
    Role selectRolePermissionByRoleId(Long roleId);

    @Transactional(rollbackFor = CommonBaseException.class)
    void rolePermissionRelSave(Long roleId, List<Long> permissions);
}
