package com.github.eiriksgata.rulateday.platform.cache;

import com.github.eiriksgata.rulateday.platform.constant.CacheNameEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service("caffeineCache")
public class CaffeineCache implements Cache {
    @Autowired
    private CacheManager caffeineCacheManager;

    @Override
    public <T> T get(CacheNameEnum cacheNameEnum, String key, Class<T> clazz) {
        log.debug("{} get -> cacheName [{}], key [{}], class type [{}]", this.getClass().getName(), cacheNameEnum, key, clazz.getName());
        return Objects.requireNonNull(caffeineCacheManager.getCache(cacheNameEnum.getCacheName())).get(key, clazz);
    }

    @Override
    public void put(CacheNameEnum cacheNameEnum, String key, Object value) {
        log.debug("{} put -> cacheName [{}], key [{}], value [{}]", this.getClass().getName(), cacheNameEnum, key, value);
        Objects.requireNonNull(caffeineCacheManager.getCache(cacheNameEnum.getCacheName())).put(key, value);
    }

    @Override
    public void remove(CacheNameEnum cacheNameEnum, String key) {

        log.debug("{} remove -> cacheName [{}], key [{}]", this.getClass().getName(), cacheNameEnum, key);
        Objects.requireNonNull(caffeineCacheManager.getCache(cacheNameEnum.getCacheName())).evict(key);
    }

    @Override
    public void clearRolePermission() {
        caffeineCacheManager.getCache(CacheNameEnum.PERMISSIONS.getCacheName()).clear();
        caffeineCacheManager.getCache(CacheNameEnum.ROLES_PERMISSIONS.getCacheName()).clear();
    }


}
