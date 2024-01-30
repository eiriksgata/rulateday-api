package com.github.eiriksgata.rulateday.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.eiriksgata.rulateday.platform.entity.UserDetail;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.Role;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.User;
import com.github.eiriksgata.rulateday.platform.service.rbac.RoleService;
import com.github.eiriksgata.rulateday.platform.service.rbac.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @Override
    public UserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("开始登陆验证，用户名为: {}", username);

        // 根据用户名验证用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getName, username);
        User userInfo = userService.getOne(queryWrapper);
        if (userInfo == null) {
            throw new UsernameNotFoundException("用户名不存在，登陆失败。");
        }

        // 构建UserDetail对象
        UserDetail userDetail = new UserDetail();
        userDetail.setUser(userInfo);
        List<Role> roleInfoList = roleService.selectRoleByUserId(userInfo.getId());
        userDetail.setRoleList(roleInfoList);
        return userDetail;
    }

}
