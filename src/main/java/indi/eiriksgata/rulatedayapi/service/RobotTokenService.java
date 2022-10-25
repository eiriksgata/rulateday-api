package indi.eiriksgata.rulatedayapi.service;

import indi.eiriksgata.rulateday.pojo.RobotToken;
import indi.eiriksgata.rulatedayapi.exception.CommonBaseException;
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
