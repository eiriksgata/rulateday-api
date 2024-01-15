package com.github.eiriksgata.rulateday.platform.service.rbac.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.mapper.UserRoleMapper;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.UserRole;
import com.github.eiriksgata.rulateday.platform.service.rbac.UserRoleRelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRoleRelServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleRelService {


    @Override
    @Transactional(rollbackFor = CommonBaseException.class)
    public void deleteRelByRoleId(Long roleId) {
        baseMapper.deleteRelByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = CommonBaseException.class)
    public void deleteRelByUserId(Long userId) {
        baseMapper.deleteRelByUserId(userId);
    }


}
