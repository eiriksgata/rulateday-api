package com.github.eiriksgata.rulateday.platform.service.rbac.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.mapper.RoleMapper;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.Role;
import com.github.eiriksgata.rulateday.platform.service.rbac.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("RoleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {


    @Override
    public List<Role> findAllRolePermission() {
        return getBaseMapper().selectAllRolePermission();
    }

    @Override
    public List<Role> selectRoleByUserId(Long userId) {
        return getBaseMapper().selectByUserId(userId);
    }

    @Override
    public void roleCreate(Role role) {
        getBaseMapper().insert(role);
    }

    @Override
    public void roleUpdate(Role role) {
        getBaseMapper().updateById(role);
    }

    @Override
    public Role roleQuery(Long roleId) {
        return getBaseMapper().selectById(roleId);
    }

    @Override
    public List<Role> getRoles() {
        return getBaseMapper().selectAllRole();
    }

    @Override
    public void deleteById(Long roleId) {
        //TODO: 需要查询出角色绑定的用户 以及 角色绑定的权限 清除相关关系后才能删除。


    }

    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public Role selectRolePermissionByRoleId(Long roleId) {
        return baseMapper.selectRolePermissionByRoleId(roleId);
    }

}
