package com.github.eiriksgata.rulateday.platform.service.impl;

import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseExceptionEnum;
import com.github.eiriksgata.rulateday.platform.pojo.RobotToken;
import com.github.eiriksgata.rulateday.platform.service.RobotTokenService;
import com.github.eiriksgata.rulateday.platform.mapper.RobotTokenMapper;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class RobotTokenServiceImpl implements RobotTokenService {

    @Autowired
    RobotTokenMapper robotTokenMapper;

    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public RobotToken saveRobotToken(String machineCode, Date expirationAt, String description) {
        RobotToken result = robotTokenMapper.selectByMachineCode(machineCode);
        if (result != null) {
            result.setExpirationAt(expirationAt);
            result.setDescription(description);
            robotTokenMapper.updateById(result);
            return result;
        }
        String name = UUID.randomUUID().toString();
        RobotToken robotToken = new RobotToken();
        robotToken.setName(name);
        robotToken.setExpirationAt(expirationAt);
        robotToken.setMachineCode(machineCode);
        robotToken.setUpdatedAt(new Date());
        robotToken.setDescription(description);
        robotToken.setCreatedAt(new Date());
        robotTokenMapper.insert(robotToken);
        return robotToken;
    }

    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public void deleteRobotToken(Integer id) {
        robotTokenMapper.deleteById(id);
    }

    @Override
    public List<RobotToken> selectAllRobotToken() {
        return robotTokenMapper.selectAll();
    }

    @Override
    public void cryptoHeadersVerification(String userId, String authorization) {
        RobotToken result = robotTokenMapper.selectByName(userId);
        if (result == null) {
            throw new CommonBaseException(CommonBaseExceptionEnum.TOKEN_NOT_EXIST_ERR);

        }
        if (BCrypt.checkpw(result.getMachineCode(), authorization)) {
            return;
        }
        throw new CommonBaseException(CommonBaseExceptionEnum.TOKEN_NOT_EXIST_ERR);
    }



}
