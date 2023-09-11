package com.github.eiriksgata.rulateday.platform.service;

import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import org.springframework.transaction.annotation.Transactional;

public interface SystemSetService {

    @Transactional(rollbackFor = CommonBaseException.class)
    void setPictureCookieSession(String cookieSession);

    @Transactional(rollbackFor = CommonBaseException.class)
    void setPictureClearance(String clearance);

    String getPictureCookieSession();

    String getPictureClearance();
}
