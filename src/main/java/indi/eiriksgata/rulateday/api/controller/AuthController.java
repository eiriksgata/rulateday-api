package indi.eiriksgata.rulateday.api.controller;

import indi.eiriksgata.rulateday.api.service.AuthService;
import indi.eiriksgata.rulateday.api.service.RobotTokenService;
import indi.eiriksgata.rulateday.pojo.RobotToken;
import indi.eiriksgata.rulateday.api.vo.ResponseBean;
import io.swagger.annotations.Api;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api
@RestController
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    RobotTokenService robotTokenService;

    @PutMapping("/authentication")
    public ResponseBean<?> authentication(@NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse response) {
        String authorization = httpServletRequest.getHeader("Authorization");
        authService.cryptoLoginVerification(authorization);
        response.setHeader("Authorization", authService.genCryptoData());
        return ResponseBean.success();
    }

    @PutMapping("/robot/token")
    public ResponseBean<?> robotTokenGen(@RequestBody RobotToken robotToken) {
        robotTokenService.saveRobotToken(robotToken.getMachineCode(), robotToken.getExpirationAt(), robotToken.getDescription());
        return ResponseBean.success();
    }

    @DeleteMapping("/robot/token/delete")
    public ResponseBean<?> robotTokenDelete(@RequestBody RobotToken robotToken) {
        robotTokenService.deleteRobotToken(robotToken.getId());
        return ResponseBean.success();
    }

    @GetMapping("/robot/token/list")
    public ResponseBean<?> getRobotTokenList() {
        return ResponseBean.success(robotTokenService.selectAllRobotToken());
    }


}
