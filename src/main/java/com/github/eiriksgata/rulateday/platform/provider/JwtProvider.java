package com.github.eiriksgata.rulateday.platform.provider;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import com.github.eiriksgata.rulateday.platform.jwt.JwtProperties;
import com.github.eiriksgata.rulateday.platform.vo.AccessToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 从请求中拿到token
     */
    public String getToken(HttpServletRequest request) {
        return request.getHeader(jwtProperties.getHeader());
    }

    /**
     * 根据用户信息生成token
     */
    public AccessToken generateToken(UserDetails userDetails) {
        return generateToken(userDetails.getUsername());
    }

    /**
     * 生成token
     * 参数是我们想放入token中的字符串
     */
    public AccessToken generateToken(String subject) {
        // 当前时间
        Date nowDate = new Date();

        // 过期时间
        Date expireDate = new Date(nowDate.getTime() + jwtProperties.getExpire() * 1000);


        String token = JWT.create()
                .setSubject(subject)
                .setIssuedAt(nowDate)
                .setExpiresAt(expireDate)
                .setKey(
                        jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8)
                ).sign();

        return AccessToken
                .builder()
                .loginAccount(subject)
                .token(token)
                .expirationTime(expireDate).build();
    }

    /**
     * 验证token是否还有效
     * <p>
     * 反解析出token中信息，然后与参数中的信息比较，再校验过期时间
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject().equals(userDetails.getUsername()) && !isTokenExpired(claims);
    }


    /**
     * 刷新token
     * 过滤器会对请求进行验证，所以这里可以不必验证
     *
     * @param oldToken 带tokenHead的token
     */
    public AccessToken refreshToken(String oldToken) {

        String token = oldToken.substring(jwtProperties.getPrefix().length());

        // token反解析
        Claims claims = getClaimsFromToken(token);

        //如果token在30分钟之内刚刷新过，返回原token
        if (tokenRefreshJustBefore(claims)) {
            return AccessToken.builder()
                    .loginAccount(claims.getSubject())
                    .token(oldToken)
                    .expirationTime(claims.getExpiration())
                    .build();
        } else {
            return generateToken(claims.getSubject());
        }
    }

    /**
     * 判断token在指定时间内是否刚刚刷新过
     */
    private boolean tokenRefreshJustBefore(Claims claims) {
        Date refreshDate = new Date();
        //刷新时间在创建时间的指定时间内
        if (refreshDate.after(claims.getExpiration()) && refreshDate.before(
                DateUtil.offsetSecond(claims.getExpiration(), 1800))) {
            return true;
        }
        return false;
    }

    /**
     * 从token中拿到负载信息
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("JWT反解析失败, token已过期或不正确, token : {}", token);
        }
        return claims;
    }


    /**
     * 从token中获取主题
     */
    public String getSubjectFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims != null) {
            return claims.getSubject();
        } else {
            return null;
        }
    }


    /**
     * 判断token是否已经过期
     */
    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }


}
