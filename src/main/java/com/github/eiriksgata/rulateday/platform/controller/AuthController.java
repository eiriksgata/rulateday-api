package com.github.eiriksgata.rulateday.platform.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.github.eiriksgata.rulateday.platform.cache.CaptchaCache;
import com.github.eiriksgata.rulateday.platform.misc.IgnoreAuthentication;
import com.github.eiriksgata.rulateday.platform.provider.JwtProvider;
import com.github.eiriksgata.rulateday.platform.service.AuthService;
import com.github.eiriksgata.rulateday.platform.service.RobotTokenService;
import com.github.eiriksgata.rulateday.platform.pojo.RobotToken;
import com.github.eiriksgata.rulateday.platform.vo.AccessToken;
import com.github.eiriksgata.rulateday.platform.vo.ResponseBean;
import com.github.eiriksgata.rulateday.platform.vo.openapi.GenCaptchaVo;
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

    @Autowired
    CaptchaCache captchaCache;

    @Autowired
    JwtProvider jwtProvider;

    @PutMapping("/authentication")
    @IgnoreAuthentication
    public ResponseBean<AccessToken> authentication(@NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse response) {
        String authorization = httpServletRequest.getHeader("Secret");
        return ResponseBean.success(authService.loginVerification(authorization));
    }

    @PostMapping("/refresh/token")
    @IgnoreAuthentication
    public ResponseBean<AccessToken> refreshToken(@RequestBody AccessToken accessToken) {
        return ResponseBean.success(
                jwtProvider.refreshToken(accessToken.getToken())
        );
    }

    @GetMapping("/logout")
    public ResponseBean<?> logout() {
        return ResponseBean.success();
    }

    @GetMapping("/generate/captcha/{username}")
    @IgnoreAuthentication
    public ResponseBean<?> generateCaptcha(@PathVariable("username") String username) {
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(
                200, 50);
        captchaCache.put(username, lineCaptcha.getCode());
        return ResponseBean.success(GenCaptchaVo.builder()
                .codeId(username)
                .pictureBase64(lineCaptcha.getImageBase64())
                .build());
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
