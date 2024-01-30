package com.github.eiriksgata.rulateday.platform.service.impl;

import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseExceptionEnum;
import com.github.eiriksgata.rulateday.platform.pojo.RobotToken;
import com.github.eiriksgata.rulateday.platform.service.RobotService;
import com.github.eiriksgata.rulateday.platform.mapper.RobotTokenMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("RobotService")
@Slf4j
public class RobotServiceImpl implements RobotService {

    @Value("${robot.open.register}")
    boolean isOpenRegister = false;

    @Autowired
    RobotTokenMapper robotTokenMapper;

    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public RobotToken save(String machineCode, Date expirationAt, String description) {
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
    public void deleteById(Integer id) {
        robotTokenMapper.deleteById(id);
    }

    @Override
    public List<RobotToken> getRobots() {
        return robotTokenMapper.selectAll();
    }

    @Override
    public RobotToken cryptoHeadersVerification(String authorization) {
        log.info("auth: {}", authorization);
        RobotToken result = robotTokenMapper.selectByName(authorization);
        if (result == null) {
            throw new CommonBaseException(CommonBaseExceptionEnum.TOKEN_NOT_EXIST_ERR);
        }
        long temp = System.currentTimeMillis();
        if (temp <= result.getExpirationAt().getTime()) {
            return result;
        }

        throw new CommonBaseException(CommonBaseExceptionEnum.TOKEN_NOT_EXIST_ERR);
    }

    @Override
    public RobotToken userOpenRegister(String machineCode, String masterCode) {
        if (!isOpenRegister) {
            throw new CommonBaseException(CommonBaseExceptionEnum.TOKEN_NOT_ACCESS_ERR);
        }
        RobotToken result = robotTokenMapper.selectByMachineCode(machineCode);
        if (result != null) {
            throw new CommonBaseException(CommonBaseExceptionEnum.MACHINE_CODE_EXISTS);
        }

        String name = UUID.randomUUID().toString();
        Date expirationAt = new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365);

        RobotToken robotToken = new RobotToken();
        robotToken.setName(name);
        robotToken.setExpirationAt(expirationAt);
        robotToken.setMachineCode(machineCode);
        robotToken.setUpdatedAt(new Date());
        robotToken.setDescription(masterCode);
        robotToken.setCreatedAt(new Date());
        robotTokenMapper.insert(robotToken);
        return robotToken;
    }

}
