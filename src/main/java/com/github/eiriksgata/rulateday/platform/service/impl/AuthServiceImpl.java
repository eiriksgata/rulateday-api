package com.github.eiriksgata.rulateday.platform.service.impl;

import cn.hutool.crypto.symmetric.AES;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.github.eiriksgata.rulateday.platform.bean.UserAuthentication;
import com.github.eiriksgata.rulateday.platform.cache.Cache;
import com.github.eiriksgata.rulateday.platform.cache.CaptchaCache;
import com.github.eiriksgata.rulateday.platform.cache.SliderCaptchaCache;
import com.github.eiriksgata.rulateday.platform.constant.CacheNameEnum;
import com.github.eiriksgata.rulateday.platform.entity.UserDetail;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseExceptionEnum;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.User;
import com.github.eiriksgata.rulateday.platform.provider.JwtProvider;
import com.github.eiriksgata.rulateday.platform.service.AuthService;
import com.github.eiriksgata.rulateday.platform.service.rbac.UserService;
import com.github.eiriksgata.rulateday.platform.utils.HexConvertUtil;
import com.github.eiriksgata.rulateday.platform.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    byte[] key = new byte[]{0x12, 0x22, 0x34, 0x55, 0x12, 0x54, 0x35, 0x06, 0x34, 0x25, 0x12, 0x42, 0x04, 0x57, 0x55, 0x16};

    byte[] iv = HexConvertUtil.hexStringToByteArray("05210352473562157534214512563228");

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    Cache caffeineCache;

    @Autowired
    CaptchaCache captchaCache;

    @Autowired
    SliderCaptchaCache sliderCaptchaCache;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;


    @Override
    public AccessToken loginVerificationByVerityCode(@NotNull String cryptoData) {
        AES aes = new AES("CBC", "PKCS7Padding", key, iv);
        byte[] decode = aes.decrypt(cryptoData);
        LoginVo<Captcha> loginVo = JSONObject.parseObject(new String(decode), new TypeReference<LoginVo<Captcha>>() {
        }.getType());

        long dataTimestamp = loginVo.getTimestamp();
        long currentTimestamp = System.currentTimeMillis();
        if (dataTimestamp < currentTimestamp - 1000 * 60 * 5 ||
                dataTimestamp > currentTimestamp + 1000 * 60 * 5) {
            throw new CommonBaseException(CommonBaseExceptionEnum.ACCOUNTS_AUTHENTICATION_ERROR);
        }

        //认证验证码
        if (!captchaCache.checkCode(loginVo.getUsername(), loginVo.getData().getCode())) {
            throw new CommonBaseException(CommonBaseExceptionEnum.ACCOUNTS_CAPTCHA_CODE_ERROR);
        }
        captchaCache.remove(loginVo.getUsername());
        return createAccessToken(loginVo.getUsername(), loginVo.getPassword());
    }

    @Override
    public AccessToken loginVerificationBySliderVerify(@NotNull String cryptoData) {
        AES aes = new AES("CBC", "PKCS7Padding", key, iv);
        byte[] decode = aes.decrypt(cryptoData);
        LoginVo<SliderCaptcha> loginVo = JSONObject.parseObject(new String(decode), new TypeReference<LoginVo<SliderCaptcha>>() {
        }.getType());

        long dataTimestamp = loginVo.getTimestamp();
        long currentTimestamp = System.currentTimeMillis();
        if (dataTimestamp < currentTimestamp - 1000 * 60 * 5 ||
                dataTimestamp > currentTimestamp + 1000 * 60 * 5) {
            throw new CommonBaseException(CommonBaseExceptionEnum.ACCOUNTS_AUTHENTICATION_ERROR);
        }

        //认证验证码
        if (!sliderCaptchaCache.verify(loginVo.getUsername(), loginVo.getData().getOffset())) {
            throw new CommonBaseException(CommonBaseExceptionEnum.ACCOUNTS_CAPTCHA_CODE_ERROR);
        }
        sliderCaptchaCache.remove(loginVo.getUsername());
        return createAccessToken(loginVo.getUsername(), loginVo.getPassword());
    }


    private AccessToken createAccessToken(String username, String password) {
        // 1 创建UsernamePasswordAuthenticationToken
        UserAuthentication usernameAuthentication = new UserAuthentication(
                username, password
        );
        // 2 认证
        Authentication authentication = authenticationManager.authenticate(usernameAuthentication);
        // 3 保存认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //获取用户信息
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();

        // 4 生成自定义token
        AccessToken accessToken = jwtProvider.generateToken(userDetail.getUsername(), userDetail.getRoles());

        // 放入缓存
        caffeineCache.put(CacheNameEnum.USER, userDetail.getUsername(), userDetail);
        return accessToken;
    }

    @Transactional(rollbackFor = CommonBaseException.class)
    @Override
    public void userPasswordReset(UserPasswordResetVo userPasswordResetVo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //获取用户信息
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        if (passwordEncoder.matches(userPasswordResetVo.getOldPassword(), userDetail.getPassword())) {
            User user = userService.selectByUsername(userDetail.getUsername());
            user.setPassword(passwordEncoder.encode(userPasswordResetVo.getNewPassword()));
            user.setUpdatedAt(new Date());
            userService.updateById(user);
            logout();
        } else {
            throw new CommonBaseException(CommonBaseExceptionEnum.ACCOUNTS_AUTHENTICATION_ERROR);
        }
    }

    @Override
    public void logout() {
        caffeineCache.remove(CacheNameEnum.USER, ((UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        SecurityContextHolder.clearContext();
    }

}
