package indi.eiriksgata.rulateday.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.eiriksgata.rulateday.api.pojo.rbac.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
  /**
   * batch insert role-permission
   *
   * @param rolePermissionList role permissions
   * @return primary key
   */
  Long batchInsert(@Param("rolePermissionList") List<RolePermission> rolePermissionList);

  /**
   * delete role-permissions
   *
   * @param roleId role id
   * @return true or false
   */
  Long deleteByRoleId(@Param("roleId") Long roleId);
}
