package com.github.eiriksgata.rulateday.platform.misc;

import com.github.eiriksgata.rulateday.platform.cache.Cache;
import com.github.eiriksgata.rulateday.platform.constant.CacheNameEnum;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.Permission;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.Role;
import com.github.eiriksgata.rulateday.platform.service.rbac.PermissionService;
import com.github.eiriksgata.rulateday.platform.service.rbac.RoleService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class InitRolePermissionCache {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private Cache caffeineCache;

    @PostConstruct
    public void init() {
        loadRolePermission();
    }

    public void loadRolePermission() {
        List<Role> rolesPermissionsList = roleService.findAllRolePermission();
        List<Permission> permissions = permissionService.list();
        permissions.forEach(permission -> {
            caffeineCache.put(CacheNameEnum.PERMISSIONS, permission.getAction() + ":" + permission.getAlias(), permission);
        });

        rolesPermissionsList.forEach(role -> {
            Map<String, Permission> permissionsMap = new HashMap<>();
            role.getPermissions().forEach(permission -> {
                permissionsMap.put(permission.getAction() + ":" + permission.getAlias(), permission);
            });
            caffeineCache.put(
                    CacheNameEnum.ROLES_PERMISSIONS, role.getCode(), permissionsMap);
        });
    }

    //如果用户更改了角色权限，那么就需要重新载入缓存数据
    public void reloadRolePermission() {
        caffeineCache.clearRolePermission();
        loadRolePermission();
    }


}
