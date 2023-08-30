package indi.eiriksgata.rulateday.api.service;

import indi.eiriksgata.rulateday.api.exception.CommonBaseException;
import indi.eiriksgata.rulateday.api.pojo.RobotToken;
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
