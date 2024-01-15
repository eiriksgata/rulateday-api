package com.github.eiriksgata.rulateday.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.RolePermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {


    /**
     * delete role-permissions
     *
     * @param roleId role id
     * @return true or false
     */
    @Delete("delete from t_rbac_rel_rp where role_id = #{roleId}")
    Long deleteRelByRoleId(@Param("roleId") Long roleId);

    @Delete("delete from t_rbac_rel_rp where permission_id = #{permissionId}")
    void deleteRelByPermissionId(@Param("permissionId") Long permissionId);

    @Select("select * from t_rbac_rel_rp where role_id = #{roleId} and permission_id = #{permissionId)")
    RolePermission selectByRoleIdAndPermissionId(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);


}
