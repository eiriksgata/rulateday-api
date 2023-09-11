package com.github.eiriksgata.rulateday.platform.service.impl;

import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseExceptionEnum;
import com.github.eiriksgata.rulateday.platform.pojo.SystemSaveData;
import com.github.eiriksgata.rulateday.platform.service.SystemSetService;
import com.github.eiriksgata.rulateday.platform.mapper.SystemSaveDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class SystemSetServiceImpl implements SystemSetService {

    @Autowired
    SystemSaveDataMapper dataMapper;

    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public void setPictureCookieSession(String cookieSession) {
        SystemSaveData resultCookieSession = dataMapper.selectByName("picture_cookie");

        if (resultCookieSession == null) {
            SystemSaveData data = new SystemSaveData();
            data.setId(null);
            data.setName("picture_cookie");
            data.setValue(cookieSession);
            data.setPath("");
            data.setUpdatedAt(new Date());
            data.setCreatedAt(new Date());
            dataMapper.insert(data);
        } else {
            resultCookieSession.setValue(cookieSession);
            resultCookieSession.setUpdatedAt(new Date());
            dataMapper.updateById(resultCookieSession);
        }

    }

    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public void setPictureClearance(String clearance) {
        SystemSaveData resultClearance = dataMapper.selectByName("picture_clearance");
        if (resultClearance == null) {
            SystemSaveData data = new SystemSaveData();
            data.setId(null);
            data.setName("picture_clearance");
            data.setValue(clearance);
            data.setPath("");
            data.setUpdatedAt(new Date());
            data.setCreatedAt(new Date());
            dataMapper.insert(data);
        } else {
            resultClearance.setValue(clearance);
            resultClearance.setUpdatedAt(new Date());
            dataMapper.updateById(resultClearance);
        }
    }


    @Override
    public String getPictureCookieSession() {
        SystemSaveData result = dataMapper.selectByName("picture_cookie");
        if (result == null || result.getValue() == null || result.getValue().equals("")) {
            throw new CommonBaseException(CommonBaseExceptionEnum.PICTURE_RANDOM_GEN_COOKIE_NULL);
        }
        return result.getValue();
    }

    @Override
    public String getPictureClearance() {
        SystemSaveData resultClearance = dataMapper.selectByName("picture_clearance");
        if (resultClearance == null || resultClearance.getValue() == null || resultClearance.getValue().equals("")) {
            throw new CommonBaseException(CommonBaseExceptionEnum.PICTURE_RANDOM_GEN_COOKIE_NULL);
        }
        return resultClearance.getValue();
    }

}
