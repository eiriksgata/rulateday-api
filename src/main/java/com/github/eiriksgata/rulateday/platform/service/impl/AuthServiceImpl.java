package com.github.eiriksgata.rulateday.platform.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.alibaba.fastjson.JSONObject;
import com.github.eiriksgata.rulateday.platform.cache.Cache;
import com.github.eiriksgata.rulateday.platform.constant.CacheNameEnum;
import com.github.eiriksgata.rulateday.platform.entity.UserDetail;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseExceptionEnum;
import com.github.eiriksgata.rulateday.platform.mapper.UserMapper;
import com.github.eiriksgata.rulateday.platform.provider.JwtProvider;
import com.github.eiriksgata.rulateday.platform.service.AuthService;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.User;
import com.github.eiriksgata.rulateday.platform.utils.ExceptionUtils;
import com.github.eiriksgata.rulateday.platform.vo.AccessToken;
import com.github.eiriksgata.rulateday.platform.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.AuthProvider;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    byte[] key = new byte[]{0x12, 0x22, 0x34, 0x55, 0x12, 0x54, 0x35, 0x6, 0x34, 0x25, 0x12, 0x42, 0x4, 0x57, 0x55, 0x16};

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

    @Override
    public AccessToken loginVerification(@NotNull String cryptoData) {
        byte[] originalData = Base64.decode(cryptoData.getBytes(StandardCharsets.UTF_8));

        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);

        byte[] decode = aes.encrypt(originalData);

        LoginVo loginVo = JSONObject.parseObject(new String(decode), LoginVo.class);
        long dataTimestamp = loginVo.getTimestamp();
        long currentTimestamp = System.currentTimeMillis();
        if (dataTimestamp < currentTimestamp - 1000 * 60 * 5 ||
                dataTimestamp > currentTimestamp + 1000 * 60 * 5) {
            throw new CommonBaseException(CommonBaseExceptionEnum.ACCOUNTS_AUTHENTICATION_ERROR);
        }

        // 1 创建UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken usernameAuthentication = new UsernamePasswordAuthenticationToken(
                loginVo.getUsername(), loginVo.getPassword()
        );
        // 2 认证
        Authentication authentication = authenticationManager.authenticate(usernameAuthentication);
        // 3 保存认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 4 生成自定义token
        AccessToken accessToken = jwtProvider.generateToken((UserDetails) authentication.getPrincipal());

        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        // 放入缓存
        caffeineCache.put(CacheNameEnum.USER, userDetail.getUsername(), userDetail);

        return accessToken;
    }


    public void logout() {
        caffeineCache.remove(CacheNameEnum.USER, ((UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        SecurityContextHolder.clearContext();
    }

    @Override
    public void cryptoHeadersVerification(String cryptoData) {
        if (cryptoData == null) {
            throw new CommonBaseException(CommonBaseExceptionEnum.TOKEN_NOT_EXIST_ERR);
        }
        try {

            return;

        } catch (Exception e) {
            log.error("token:{},无法验证:{}", cryptoData, ExceptionUtils.getExceptionAllInfo(e));
        }
        throw new CommonBaseException(CommonBaseExceptionEnum.TOKEN_NOT_EXIST_ERR);
    }


}
