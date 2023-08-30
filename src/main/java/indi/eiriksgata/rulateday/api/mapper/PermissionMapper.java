package indi.eiriksgata.rulateday.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.eiriksgata.rulateday.api.pojo.rbac.Permission;
import indi.eiriksgata.rulateday.api.qo.PermissionQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
  /**
   * select permission list
   *
   * @param permissionQuery permission query object
   * @return permission list
   */
  List<Permission> selectPermissions(@Param("permissionQuery") PermissionQuery permissionQuery);

  /**
   * select permissions by role id
   *
   * @param roleId role id
   * @return role-permissions
   */
  List<Permission> selectByRoleId(@Param("roleId") Long roleId);
}
