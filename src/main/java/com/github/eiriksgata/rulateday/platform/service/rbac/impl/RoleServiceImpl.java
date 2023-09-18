package com.github.eiriksgata.rulateday.platform.service.rbac.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.eiriksgata.rulateday.platform.mapper.RoleMapper;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.Role;
import com.github.eiriksgata.rulateday.platform.service.rbac.RoleService;
import org.springframework.stereotype.Service;

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

}
