package com.github.eiriksgata.rulateday.platform.misc;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.eiriksgata.rulateday.platform.cache.Cache;
import com.github.eiriksgata.rulateday.platform.constant.CacheNameEnum;
import com.github.eiriksgata.rulateday.platform.entity.UserDetail;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.Permission;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
        log.debug("进入自定义鉴权投票器，URI : {} {}", method, requestUrl);

        String key = method + ":" + requestUrl;
        // 如果没有缓存中没有此权限也就是未保护此API，弃权
        Permission permission = caffeineCache.get(CacheNameEnum.PERMISSIONS, key, Permission.class);
        if (permission == null) {
            return ACCESS_ABSTAIN;
        }

        // 拿到当前用户所具有的权限
        List<String> roles = ((UserDetail) authentication.getPrincipal()).getRoles();

        for (String role : roles) {
            Map<String, Permission> rolePermissionMap = caffeineCache.get(CacheNameEnum.ROLES_PERMISSIONS, role, Map.class);
            Permission tempPermission = rolePermissionMap.get(key);
            if (tempPermission == null) {
                return ACCESS_DENIED;
            }
        }
        return ACCESS_GRANTED;
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
