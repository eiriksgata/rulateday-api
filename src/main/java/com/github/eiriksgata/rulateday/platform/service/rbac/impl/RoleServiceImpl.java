package com.github.eiriksgata.rulateday.platform.service.rbac.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.mapper.RoleMapper;
import com.github.eiriksgata.rulateday.platform.mapper.RolePermissionMapper;
import com.github.eiriksgata.rulateday.platform.misc.InitRolePermissionCache;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.Role;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.RolePermission;
import com.github.eiriksgata.rulateday.platform.service.rbac.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("RoleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @Autowired
    InitRolePermissionCache initRolePermissionCache;


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
        baseMapper.deleteById(roleId);


    }

    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public Role selectRolePermissionByRoleId(Long roleId) {
        return baseMapper.selectRolePermissionByRoleId(roleId);
    }

    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public void rolePermissionRelSave(Long roleId, List<Long> permissions) {
        rolePermissionMapper.deleteRelByRoleId(roleId);
        for (Long permissionId : permissions) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermission.setCreatedAt(new Date());
            rolePermission.setUpdatedAt(new Date());
            rolePermissionMapper.insert(rolePermission);
        }
        initRolePermissionCache.reloadRolePermission();

    }

}
