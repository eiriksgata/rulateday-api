package com.github.eiriksgata.rulateday.platform.service.rbac;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService extends IService<User> {
    @Transactional(rollbackFor = CommonBaseException.class)
    void updatePasswordByUserId(long userId, String password);

    User selectByUsername(String username);


    List<User> urpList();

    void delete(Long userId);

    void roleCreate(User user);

    void roleUpdate(User user);
}
