package com.github.eiriksgata.rulateday.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.UserRole;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}
