package com.github.eiriksgata.rulateday.platform.service.rbac.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.eiriksgata.rulateday.platform.mapper.PermissionMapper;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.Permission;
import com.github.eiriksgata.rulateday.platform.service.rbac.PermissionService;
import org.springframework.stereotype.Service;

@Service("PermissionService")
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {




}
