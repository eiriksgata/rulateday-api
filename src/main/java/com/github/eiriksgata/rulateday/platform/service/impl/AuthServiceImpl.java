package com.github.eiriksgata.rulateday.platform.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.symmetric.AES;
import com.alibaba.fastjson.JSONObject;
import com.github.eiriksgata.rulateday.platform.bean.UserAuthentication;
import com.github.eiriksgata.rulateday.platform.cache.Cache;
import com.github.eiriksgata.rulateday.platform.cache.CaptchaCache;
import com.github.eiriksgata.rulateday.platform.constant.CacheNameEnum;
import com.github.eiriksgata.rulateday.platform.entity.UserDetail;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseExceptionEnum;
import com.github.eiriksgata.rulateday.platform.mapper.UserMapper;
import com.github.eiriksgata.rulateday.platform.provider.JwtProvider;
import com.github.eiriksgata.rulateday.platform.service.AuthService;
import com.github.eiriksgata.rulateday.platform.utils.HexConvertUtil;
import com.github.eiriksgata.rulateday.platform.vo.AccessToken;
import com.github.eiriksgata.rulateday.platform.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    byte[] key = new byte[]{0x12, 0x22, 0x34, 0x55, 0x12, 0x54, 0x35, 0x06, 0x34, 0x25, 0x12, 0x42, 0x04, 0x57, 0x55, 0x16};

    byte[] iv = HexConvertUtil.hexStringToByteArray("05210352473562157534214512563228");

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    Cache caffeineCache;

    @Autowired
    CaptchaCache captchaCache;

    @Override
    public AccessToken loginVerification(@NotNull String cryptoData) {
        byte[] originalData = Base64.decode(cryptoData.getBytes(StandardCharsets.UTF_8));

        AES aes = new AES("CBC", "PKCS7Padding", key, iv);
        byte[] decode = aes.decrypt(cryptoData);
        LoginVo loginVo = JSONObject.parseObject(new String(decode), LoginVo.class);

        long dataTimestamp = loginVo.getTimestamp();
        long currentTimestamp = System.currentTimeMillis();
        if (dataTimestamp < currentTimestamp - 1000 * 60 * 5 ||
                dataTimestamp > currentTimestamp + 1000 * 60 * 5) {
            throw new CommonBaseException(CommonBaseExceptionEnum.ACCOUNTS_AUTHENTICATION_ERROR);
        }

        //认证验证码

        if (!captchaCache.checkCode(loginVo.getUsername(), loginVo.getCaptcha())) {
            throw new CommonBaseException(CommonBaseExceptionEnum.ACCOUNTS_CAPTCHA_CODE_ERROR);
        } else {
            captchaCache.remove(loginVo.getUsername());
        }


        // 1 创建UsernamePasswordAuthenticationToken
        UserAuthentication usernameAuthentication = new UserAuthentication(
                loginVo.getUsername(), loginVo.getPassword()
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


    public void logout() {
        caffeineCache.remove(CacheNameEnum.USER, ((UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        SecurityContextHolder.clearContext();
    }

}
