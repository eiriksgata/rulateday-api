package com.github.eiriksgata.rulateday.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * select permission list
     *
     * @param parentId permission id
     * @return permission list
     */

    @Select("select * from t_rbac_permission where parent_id =#{parentId}")
    List<Permission> selectPermissions(@Param("parentId") Long parentId);

    /**
     * select permissions by role id
     *
     * @param roleId role id
     * @return role-permissions
     */
    @Select("SELECT p.id,p.parent_id,p.name,p.alias,p.action,p.intro,p.created_at,p.updated_at\n" +
            "FROM t_rbac_rel_rp rp\n" +
            "INNER JOIN t_rbac_role r ON rp.role_id = r.id\n" +
            "INNER JOIN t_rbac_permission p ON rp.permission_id = p.id\n" +
            "WHERE r.id =  #{roleId}")
    List<Permission> selectByRoleId(@Param("roleId") Long roleId);


}
