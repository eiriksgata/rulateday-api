package com.github.eiriksgata.rulateday.platform.service.rbac;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.Permission;
import org.springframework.transaction.annotation.Transactional;

public interface PermissionService extends IService<Permission> {
    @Transactional(rollbackFor = CommonBaseException.class)
    void deleteByPermissionId(Long permissionId);
}
