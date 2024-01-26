package com.github.eiriksgata.rulateday.platform.controller;

import com.github.eiriksgata.rulateday.platform.entity.UserDetail;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseExceptionEnum;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.Permission;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.Role;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.User;
import com.github.eiriksgata.rulateday.platform.provider.JwtProvider;
import com.github.eiriksgata.rulateday.platform.service.rbac.PermissionService;
import com.github.eiriksgata.rulateday.platform.service.rbac.RoleService;
import com.github.eiriksgata.rulateday.platform.service.rbac.UserRoleRelService;
import com.github.eiriksgata.rulateday.platform.service.rbac.UserService;
import com.github.eiriksgata.rulateday.platform.vo.ResponseBean;
import com.github.eiriksgata.rulateday.platform.vo.RolePermissionsRelVo;
import com.github.eiriksgata.rulateday.platform.vo.TokenGenerateVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "RBAC")
@RequestMapping("")
public class RBACController {

    @Autowired
    RoleService roleService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    UserService userService;

    @Autowired
    UserRoleRelService userRoleRelService;

    @Autowired
    JwtProvider jwtProvider;

    @PutMapping("/role")
    public ResponseBean<?> roleSave(@RequestBody Role role) {
        if (role.getId() == -1) role.setId(null);
        roleService.saveOrUpdate(role);
        return ResponseBean.success();
    }

    @GetMapping("/role/{id}")
    public ResponseBean<Role> roleQuery(@PathVariable("id") long roleId) {
        return ResponseBean.success(
                roleService.roleQuery(roleId)
        );
    }

    @GetMapping("/roles")
    public ResponseBean<?> roleListQuery() {
        return ResponseBean.success(
                roleService.getRoles()
        );
    }

    @DeleteMapping("/role/{id}")
    public ResponseBean<?> roleRemove(@PathVariable("id") long roleId) {
        roleService.deleteById(roleId);
        return ResponseBean.success();
    }

    @PutMapping("/permission")
    public ResponseBean<?> permissionSave(@RequestBody Permission permission) {
        if (permission.getId() == -1) permission.setId(null);
        permissionService.saveOrUpdate(permission);
        return ResponseBean.success();
    }


    @GetMapping("/permission/{id}")
    public ResponseBean<Permission> permissionQuery(@PathVariable("id") long permissionId) {
        return ResponseBean.success(
                permissionService.getById(permissionId)
        );
    }

    @DeleteMapping("/permission/{id}")
    public ResponseBean<?> permissionRemove(@PathVariable("id") long permissionId) {
        permissionService.deleteByPermissionId(permissionId);
        return ResponseBean.success();
    }

    @GetMapping("/permissions")
    public ResponseBean<List<Permission>> permissionList() {
        return ResponseBean.success(
                permissionService.list()
        );
    }

    @GetMapping("/permission-tree")
    public ResponseBean<?> permissionTree() {
        //TODO: 返回树结构数据
        return ResponseBean.success();
    }

    @PutMapping("/user/create")
    public ResponseBean<?> userCreate(@RequestBody User user) {
        if (user.getName().length() < 3) {
            throw new CommonBaseException(CommonBaseExceptionEnum.ACCOUNTS_USERNAME_LENGTH_ERROR);
        }
        userService.roleCreate(user);
        return ResponseBean.success();
    }

    @PutMapping("/user")
    public ResponseBean<?> userUpdate(@RequestBody User user) {
        if (user.getName().length() < 3) {
            throw new CommonBaseException(CommonBaseExceptionEnum.ACCOUNTS_USERNAME_LENGTH_ERROR);
        }
        userService.roleUpdate(user);
        return ResponseBean.success();
    }

    @GetMapping("/users")
    public ResponseBean<?> getUserList() {
        return ResponseBean.success(
                userService.urpList()
        );
    }

    @DeleteMapping("/user/{id}")
    public ResponseBean<?> userRemove(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseBean.success();
    }

    @PutMapping("/user/password")
    public ResponseBean<?> userPasswordUpdate(@RequestBody User user) {
        userService.updatePasswordByUserId(user.getId(), user.getPassword());
        return ResponseBean.success();
    }


    @GetMapping("/role/permissions/{roleId}")
    public ResponseBean<?> getRolePermissionsByRoleId(@Validated @PathVariable("roleId") Long roleId) {
        return ResponseBean.success(
                roleService.selectRolePermissionByRoleId(roleId)
        );
    }

    @PutMapping("/role/permissions/save")
    public ResponseBean<?> rolePermissionSave(@RequestBody RolePermissionsRelVo rolePermissionsRelVo) {
        roleService.rolePermissionRelSave(rolePermissionsRelVo.getRoleId(), rolePermissionsRelVo.getPermissions());
        return ResponseBean.success();
    }

    @GetMapping("/permissions/roles")
    public ResponseBean<?> getPermissionsRoles() {
        return ResponseBean.success(
                roleService.findAllRolePermission()
        );
    }

    @PutMapping("/token/generate")
    public ResponseBean<?> tokenGenerate(@RequestBody TokenGenerateVo tokenGenerateVo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //获取用户信息
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        return ResponseBean.success(
                jwtProvider.generateToken(
                        userDetail.getUsername(),
                        userDetail.getRoles(),
                        tokenGenerateVo.getIssuedAt(),
                        tokenGenerateVo.getExpireDate())
        );
    }

}
