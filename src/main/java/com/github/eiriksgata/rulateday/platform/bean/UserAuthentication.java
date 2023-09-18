package com.github.eiriksgata.rulateday.platform.bean;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthentication extends UsernamePasswordAuthenticationToken {

    private String verifyCode;
    private String verifyId;

    /**
     * @param principal   用户名
     * @param credentials 密码
     */
    public UserAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public UserAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public UserAuthentication(Object principal, Object credentials, String verifyCode, String verifyId) {
        super(principal, credentials);
        this.verifyCode = verifyCode;
        this.verifyId = verifyId;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public String getVerifyId() {
        return verifyId;
    }


}
