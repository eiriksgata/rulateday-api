package com.github.eiriksgata.rulateday.platform.service.rbac.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.mapper.PermissionMapper;
import com.github.eiriksgata.rulateday.platform.mapper.RolePermissionMapper;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.Permission;
import com.github.eiriksgata.rulateday.platform.service.rbac.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("PermissionService")
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @Override
    @Transactional(rollbackFor = CommonBaseException.class)
    public void deleteByPermissionId(Long permissionId) {
        rolePermissionMapper.deleteRelByPermissionId(permissionId);
        baseMapper.deleteById(permissionId);
    }






}
