package com.github.eiriksgata.rulateday.platform.controller;

import com.github.eiriksgata.rulateday.platform.pojo.rbac.Permission;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.Role;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.User;
import com.github.eiriksgata.rulateday.platform.service.rbac.PermissionService;
import com.github.eiriksgata.rulateday.platform.service.rbac.RoleService;
import com.github.eiriksgata.rulateday.platform.service.rbac.UserService;
import com.github.eiriksgata.rulateday.platform.vo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RbacController {

    @Autowired
    RoleService roleService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    UserService userService;

    @PostMapping("/role")
    public ResponseBean<?> roleCreate(@RequestBody Role role) {
        roleService.roleCreate(role);
        return ResponseBean.success();
    }

    @PutMapping("/role")
    public ResponseBean<?> roleUpdate(@RequestBody Role role) {
        roleService.roleUpdate(role);
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

    @PostMapping("/permission")
    public ResponseBean<?> permissionCreate(@RequestBody Permission permission) {
        permission.setId(null);
        permissionService.save(permission);
        return ResponseBean.success();
    }

    @PutMapping("/permission")
    public ResponseBean<?> permissionUpdate(@RequestBody Permission permission) {
        permissionService.updateById(permission);
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
        //TODO
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

    @PostMapping("/user")
    public ResponseBean<?> accountCreate(@RequestBody User user) {
        userService.updateById(user);
        return ResponseBean.success();

    }


}
