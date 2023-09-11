package com.github.eiriksgata.rulateday.platform.cache;


import com.github.eiriksgata.rulateday.platform.constant.CacheNameEnum;

import java.lang.reflect.Type;

public interface Cache {

    <T> T get(CacheNameEnum cacheNameEnum, String key, Class<T> clazz);

    void put(CacheNameEnum cacheNameEnum, String key, Object value);

    void remove(CacheNameEnum cacheNameEnum, String key);

    void clearRolePermission();
}
