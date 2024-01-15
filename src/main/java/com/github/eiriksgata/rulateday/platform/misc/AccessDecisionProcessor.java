package com.github.eiriksgata.rulateday.platform.misc;

import cn.hutool.core.text.AntPathMatcher;
import com.github.eiriksgata.rulateday.platform.cache.Cache;
import com.github.eiriksgata.rulateday.platform.constant.CacheNameEnum;
import com.github.eiriksgata.rulateday.platform.entity.UserDetail;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.Permission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class AccessDecisionProcessor implements AccessDecisionVoter<FilterInvocation> {

    @Autowired
    private Cache caffeineCache;

    @Override
    public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute> attributes) {
        assert authentication != null;
        assert object != null;

        // 拿到当前请求uri
        String requestUrl = object.getRequestUrl();
        String method = object.getRequest().getMethod();
//      log.info("进入自定义鉴权投票器，URI : {} {}", method, requestUrl);
        String permissionName = method + ":" + requestUrl;
        // 如果没有缓存中没有此权限也就是未保护此API，弃权
//        Permission permission = caffeineCache.get(CacheNameEnum.PERMISSIONS, permissionName, Permission.class);
//        if (permission == null) {
//            log.info("访问弃权");
//            return ACCESS_ABSTAIN;
//        }

        //检测是否是超级管理员账号，如果是则弃权
        String username = ((UserDetail) authentication.getPrincipal()).getUser().getName();
        if (Objects.equals(username, "admin")) {
            return ACCESS_ABSTAIN;
        }

        // 拿到当前用户所具有的权限
        List<String> roles = ((UserDetail) authentication.getPrincipal()).getRoles();

        for (String role : roles) {
            Map<String, Permission> rolePermissionMap = caffeineCache.get(
                    CacheNameEnum.ROLES_PERMISSIONS, role, Map.class);
            Permission tempPermission = rolePermissionMap.get(permissionName);
            AntPathMatcher antPathMatcher = new AntPathMatcher();
            if (tempPermission == null) {
                if (rolePermissionMap.keySet().stream().anyMatch(
                        (name) -> antPathMatcher.match(name, permissionName))) return ACCESS_GRANTED;
            } else {
                return ACCESS_GRANTED;
            }
        }

        log.info("拒绝访问");
        return ACCESS_DENIED;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
