package com.github.eiriksgata.rulateday.platform.cache;

import com.github.eiriksgata.rulateday.platform.entity.TemplateCutResult;
import com.github.eiriksgata.cache.Cache;
import org.springframework.stereotype.Component;

@Component
public class SliderCaptchaCache {

    private final Cache<TemplateCutResult> cache = new Cache<>();

    public void put(String codeId, TemplateCutResult templateCutResult) {
        cache.set(codeId, templateCutResult, 1000L * 60 * 2);
    }

    public void remove(String codeId) {
        cache.remove(codeId);
    }

    public boolean verify(String codeId, int xWidth) {
        TemplateCutResult templateCutResult = cache.get(codeId);
        if (templateCutResult == null) {
            return false;
        }
        return xWidth > templateCutResult.getX() - 10
                && xWidth < templateCutResult.getX() + 10;
    }


}
