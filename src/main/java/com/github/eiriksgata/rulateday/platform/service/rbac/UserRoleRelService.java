package com.github.eiriksgata.rulateday.platform.service.rbac;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.UserRole;
import org.springframework.transaction.annotation.Transactional;

public interface UserRoleRelService extends IService<UserRole> {
    @Transactional(rollbackFor = CommonBaseException.class)
    void deleteRelByRoleId(Long roleId);

    @Transactional(rollbackFor = CommonBaseException.class)
    void deleteRelByUserId(Long userId);
}
