package indi.eiriksgata.rulatedayapi.service;

import indi.eiriksgata.cache.Cache;
import org.springframework.stereotype.Component;

@Component
public class CacheService {
    private final Cache<String> tokenCache = new Cache<>();

    public void put(String token) {
        tokenCache.set(token, "0", 1000L * 60 * 60 * 24 * 30);
    }

    public boolean verification(String token) {
        try {
            String result = tokenCache.get(token);
            return result != null;
        } catch (Exception e) {
            return false;
        }

    }
}
