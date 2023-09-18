package com.github.eiriksgata.rulateday.platform.misc;

import cn.hutool.core.util.StrUtil;
import com.github.eiriksgata.rulateday.platform.cache.Cache;
import com.github.eiriksgata.rulateday.platform.constant.CacheNameEnum;
import com.github.eiriksgata.rulateday.platform.entity.UserDetail;
import com.github.eiriksgata.rulateday.platform.jwt.JwtProperties;
import com.github.eiriksgata.rulateday.platform.provider.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    Cache caffeineCache;

    @Override
    protected void doFilterInternal( HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain chain) throws ServletException, IOException {

        // 拿到Authorization请求头内的信息
        String authToken = jwtProvider.getToken(request);

        // 判断一下内容是否为空
        if (StrUtil.isNotEmpty(authToken) && authToken.startsWith(jwtProperties.getPrefix())) {
            // 去掉token前缀(Bearer )，拿到真实token
            authToken = authToken.substring(jwtProperties.getPrefix().length());

            // 拿到token里面的登录账号
            String loginAccount = jwtProvider.getSubjectFromToken(authToken);

            if (StrUtil.isNotEmpty(loginAccount) && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 查询用户
                UserDetail userDetails = caffeineCache.get(CacheNameEnum.USER, loginAccount, UserDetail.class);

                // 拿到用户信息后验证用户信息与token
                if (userDetails != null && jwtProvider.validateToken(authToken, userDetails)) {

                    // 组装authentication对象，构造参数是Principal Credentials 与 Authorities
                    // 后面的拦截器里面会用到 grantedAuthorities 方法
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, userDetails.getPassword(), userDetails.getAuthorities()
                            );

                    // 将authentication信息放入到上下文对象中
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    log.info("JWT过滤器通过校验请求头token自动登录成功, user : {}", userDetails.getUsername());
                }
            }
        }
        chain.doFilter(request, response);

    }
}
