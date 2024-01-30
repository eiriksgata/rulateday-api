package com.github.eiriksgata.rulateday.platform.controller;

import com.github.eiriksgata.rulateday.platform.misc.IgnoreAuthentication;
import com.github.eiriksgata.rulateday.platform.pojo.RobotToken;
import com.github.eiriksgata.rulateday.platform.service.RobotService;
import com.github.eiriksgata.rulateday.platform.vo.ResponseBean;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
@RequestMapping("/api/v1")
public class RobotController {

    @Autowired
    RobotService robotService;

    @PutMapping("/robot")
    public ResponseBean<?> robotGen(@RequestBody RobotToken robotToken) {
        robotService.save(robotToken.getMachineCode(), robotToken.getExpirationAt(), robotToken.getDescription());
        return ResponseBean.success();
    }

    @DeleteMapping("/robot/{id}")
    public ResponseBean<?> robotDelete(@PathVariable("id") Integer id) {
        robotService.deleteById(id);
        return ResponseBean.success();
    }

    @GetMapping("/robot/list")
    public ResponseBean<?> getRobotList() {
        return ResponseBean.success(robotService.getRobots());
    }

    @IgnoreAuthentication
    @PutMapping("/robot/open/register")
    public ResponseBean<?> robotOpenRegister(@RequestBody RobotToken robotToken) {
        return ResponseBean.success(
                robotService.userOpenRegister(robotToken.getMachineCode(), robotToken.getDescription())
        );
    }


}
