package com.github.eiriksgata.rulateday.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.RolePermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {


    /**
     * delete role-permissions
     *
     * @param roleId role id
     * @return true or false
     */
    @Delete("delete from t_rbac_rp where role_id = #{roleId}")
    Long deleteByRoleId(@Param("roleId") Long roleId);
}
