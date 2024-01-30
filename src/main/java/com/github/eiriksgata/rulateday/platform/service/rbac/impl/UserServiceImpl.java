package com.github.eiriksgata.rulateday.platform.service.rbac.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseExceptionEnum;
import com.github.eiriksgata.rulateday.platform.mapper.UserMapper;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.User;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.UserRole;
import com.github.eiriksgata.rulateday.platform.service.rbac.UserRoleRelService;
import com.github.eiriksgata.rulateday.platform.service.rbac.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserRoleRelService userRoleRelService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public void updatePasswordByUserId(long userId, String password) {
        baseMapper.updatePasswordByUserId(password, userId);
    }


    @Override
    public User selectByUsername(String username) {
        return baseMapper.selectByName(username);
    }


    @Override
    public List<User> urpList() {
        return baseMapper.selectUrpList();
    }

    @Override
    public void delete(Long userId) {
        userRoleRelService.deleteRelByUserId(userId);

        //remove
        baseMapper.deleteById(userId);
    }

    @Override
    @Transactional(rollbackFor = CommonBaseException.class)
    public void roleCreate(User user) {
        User result = selectByUsername(user.getName());
        if (result != null) {
            throw new CommonBaseException(CommonBaseExceptionEnum.ACCOUNTS_USERNAME_EXIST_ERROR);
        }
        user.setId(null);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user.setIsDeleted(false);
        user.setIsEnabled(true);
        user.setSeries(null);
        user.setToken(null);
        user.setPassword(passwordEncoder.encode(user.getPwd()));
        save(user);

        userRoleRelHandler(user.getId(), user.getRoles());

    }


    @Override
    @Transactional(rollbackFor = CommonBaseException.class)
    public void roleUpdate(User user) {
        User result = selectByUsername(user.getName());
        if (result == null) {
            throw new CommonBaseException(CommonBaseExceptionEnum.ACCOUNTS_USERNAME_NOT_EXIST_ERROR);
        }
        user.setId(result.getId());
        user.setCreatedAt(result.getCreatedAt());
        user.setUpdatedAt(new Date());
        user.setIsDeleted(result.getIsDeleted());
        user.setIsEnabled(result.getIsEnabled());
        user.setSeries(result.getSeries());
        user.setToken(result.getToken());
        user.setPassword(result.getPassword());
        updateById(user);

        userRoleRelHandler(user.getId(), user.getRoles());

    }

    @Transactional(rollbackFor = CommonBaseException.class)
    private void userRoleRelHandler(Long userId, List<Long> roles) {
        //删除关系
        userRoleRelService.deleteRelByUserId(userId);

        if(roles == null){
            return;
        }
        //添加关系
        for (Long roleId : roles) {
            UserRole userRoleRel = new UserRole();
            userRoleRel.setRoleId(roleId);
            userRoleRel.setUserId(userId);
            userRoleRel.setCreatedAt(new Date());
            userRoleRel.setUpdatedAt(new Date());
            userRoleRelService.save(userRoleRel);
        }
    }

}
