package com.github.eiriksgata.rulateday.platform.cache;

import indi.eiriksgata.cache.Cache;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CaptchaCache {

    private final Cache<String> cache = new Cache<>();

    public void put(String codeId, String value) {
        cache.set(codeId, value, 1000L * 60 * 2);
    }

    public void remove(String codeId) {
        cache.remove(codeId);
    }

    public boolean checkCode(String codeId, String value) {
        String checkValue = cache.get(codeId);
        if (checkValue == null) {
            return false;
        }
        return Objects.equals(checkValue, value);
    }


}
