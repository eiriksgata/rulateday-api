package com.github.eiriksgata.rulateday.platform.service.rbac.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.eiriksgata.rulateday.platform.mapper.UserMapper;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.User;
import com.github.eiriksgata.rulateday.platform.service.rbac.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {



}
