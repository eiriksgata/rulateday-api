package com.github.eiriksgata.rulateday.platform.service;

import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.pojo.RobotToken;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface RobotTokenService {
    @Transactional(rollbackFor = CommonBaseException.class)
    RobotToken saveRobotToken(String machineCode, Date expirationAt, String description);

    @Transactional(rollbackFor = CommonBaseException.class)
    void deleteRobotToken(Integer id);

    List<RobotToken> selectAllRobotToken();

    void cryptoHeadersVerification(String userId, String authorization);
}
