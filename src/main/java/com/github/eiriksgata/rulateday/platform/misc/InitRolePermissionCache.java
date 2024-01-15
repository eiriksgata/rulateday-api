package com.github.eiriksgata.rulateday.platform.misc;

import com.github.eiriksgata.rulateday.platform.cache.Cache;
import com.github.eiriksgata.rulateday.platform.constant.CacheNameEnum;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.Permission;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.Role;
import com.github.eiriksgata.rulateday.platform.service.rbac.PermissionService;
import com.github.eiriksgata.rulateday.platform.service.rbac.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
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
        //log.info("{}", rolesPermissionsList);
        List<Permission> permissions = permissionService.list();
        permissions.forEach(permission -> {
            if (!Objects.equals(permission.getAction(), "") && Objects.equals(permission.getAction(), "")) {
                caffeineCache.put(CacheNameEnum.PERMISSIONS, permission.getAction() + ":" + permission.getAlias(), permission);
            }
        });

        rolesPermissionsList.forEach(role -> {
            Map<String, Permission> permissionsMap = new HashMap<>();
            role.getPermissions().forEach(permission -> {
                if (!Objects.equals(permission.getAction(), "") && Objects.equals(permission.getAction(), "")) {
                    permissionsMap.put(permission.getAction() + ":" + permission.getAlias(), permission);
                    //log.info(permission.getAction() + ":" + permission.getAlias());
                }
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
