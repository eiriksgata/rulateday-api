package com.github.eiriksgata.rulateday.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.UserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    @Delete("delete from t_rbac_rel_ur where role_id = #{roleId}")
    Long deleteRelByRoleId(@Param("roleId") Long roleId);

    @Delete("delete from t_rbac_rel_ur where user_id = #{userId}")
    void deleteRelByUserId(@Param("userId") Long userId);

}
