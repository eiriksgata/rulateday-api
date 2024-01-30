package com.github.eiriksgata.rulateday.platform.service;

import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.pojo.RobotToken;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface RobotService {
    @Transactional(rollbackFor = CommonBaseException.class)
    RobotToken save(String machineCode, Date expirationAt, String description);

    @Transactional(rollbackFor = CommonBaseException.class)
    void deleteById(Integer id);

    List<RobotToken> getRobots();

    RobotToken cryptoHeadersVerification(String authorization);


    RobotToken userOpenRegister(String machineCode, String masterCode);
}
