package com.github.eiriksgata.rulateday.platform.provider;

import com.github.eiriksgata.rulateday.platform.bean.UserAuthentication;
import com.github.eiriksgata.rulateday.platform.entity.UserDetail;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseExceptionEnum;
import com.github.eiriksgata.rulateday.platform.service.impl.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserAuthentication myUserAuthentication = (UserAuthentication) authentication;
        String name = myUserAuthentication.getName();
        String password = myUserAuthentication.getCredentials().toString();
        UserDetail userDetail = customUserDetailsService.loadUserByUsername(name);

        log.info("{}\n{}", password, userDetail.getPassword());
        //验证用户密码
        if (passwordEncoder.matches(password, userDetail.getPassword())) {
            //如果账户被禁用
            if (!userDetail.isEnabled()) {
                throw new DisabledException("该用户账号被禁用");
            }
            return new UsernamePasswordAuthenticationToken(
                    userDetail,
                    null,
                    userDetail.getAuthorities());
        }
        //用户密码错误
        throw new CommonBaseException(CommonBaseExceptionEnum.ACCOUNTS_AUTHENTICATION_ERROR);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UserAuthentication.class);
    }

}
