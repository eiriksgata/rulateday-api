package com.github.eiriksgata.rulateday.platform.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.github.eiriksgata.rulateday.platform.cache.CaptchaCache;
import com.github.eiriksgata.rulateday.platform.cache.SliderCaptchaCache;
import com.github.eiriksgata.rulateday.platform.entity.TemplateCutResult;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseExceptionEnum;
import com.github.eiriksgata.rulateday.platform.misc.IgnoreAuthentication;
import com.github.eiriksgata.rulateday.platform.provider.JwtProvider;
import com.github.eiriksgata.rulateday.platform.service.AuthService;
import com.github.eiriksgata.rulateday.platform.service.RobotTokenService;
import com.github.eiriksgata.rulateday.platform.pojo.RobotToken;
import com.github.eiriksgata.rulateday.platform.utils.VerifyImageUtil;
import com.github.eiriksgata.rulateday.platform.vo.AccessToken;
import com.github.eiriksgata.rulateday.platform.vo.ResponseBean;
import com.github.eiriksgata.rulateday.platform.vo.SliderCaptchaVerifyVo;
import com.github.eiriksgata.rulateday.platform.vo.openapi.GenCaptchaVo;
import io.swagger.annotations.Api;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

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
    SliderCaptchaCache sliderCaptchaCache;

    @Autowired
    JwtProvider jwtProvider;

    @PutMapping("/authentication/captcha/slider")
    @IgnoreAuthentication
    public ResponseBean<AccessToken> authenticationBySliderCaptcha(@NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse response) {
        String authorization = httpServletRequest.getHeader("Secret");
        return ResponseBean.success(authService.loginVerificationBySliderVerify(authorization));
    }

    @PutMapping("/authentication/captcha/code")
    @IgnoreAuthentication
    public ResponseBean<AccessToken> authenticationByCaptchaCode(@NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse response) {
        String authorization = httpServletRequest.getHeader("Secret");
        return ResponseBean.success(authService.loginVerificationByVerityCode(authorization));
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

    @PostMapping("/slider/captcha/verify")
    @IgnoreAuthentication
    public ResponseBean<?> sliderCaptchaVerify(@RequestBody SliderCaptchaVerifyVo sliderCaptchaVerifyVo) {
        if (sliderCaptchaCache.verify(
                sliderCaptchaVerifyVo.getUsername(),
                sliderCaptchaVerifyVo.getOffset()
        )) {
            return ResponseBean.success();
        }
        return ResponseBean.error(CommonBaseExceptionEnum.ACCOUNTS_CAPTCHA_CODE_ERROR);
    }

    @GetMapping("/generate/slider/captcha/{username}")
    @IgnoreAuthentication
    public ResponseBean<?> generateSliderCaptcha(@PathVariable("username") String username) {
        File backgroundPicture = new File("resources/verify/background/1.jpg");
        File templatePicture = new File("resources/verify/template/1.png");
        try {
            TemplateCutResult result = VerifyImageUtil.pictureTemplatesCut(templatePicture, backgroundPicture);
            sliderCaptchaCache.put(username, result);
            return ResponseBean.success(
                    TemplateCutResult.builder()
                            .slider(result.getSlider())
                            .background(result.getBackground())
                            .width(result.getWidth())
                            .height(result.getHeight())
                            .y(result.getY())
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBean.error("生成验证码信息错误。");
        }
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
