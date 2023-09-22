package com.github.eiriksgata.rulateday.platform.entity;

import com.github.eiriksgata.rulateday.platform.pojo.rbac.Role;
import com.github.eiriksgata.rulateday.platform.pojo.rbac.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class UserDetail implements Serializable, UserDetails {

    private User user;

    private Collection<? extends GrantedAuthority> grantedAuthorities;

    private List<Role> roleList;

    private List<String> roles;

    public Long getUserId() {
        return this.user.getId();
    }

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (grantedAuthorities != null) return this.grantedAuthorities;
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<String> authorities = new ArrayList<>();
        roleList.forEach(role -> {
            authorities.add(role.getCode());
            grantedAuthorities.add(new SimpleGrantedAuthority(
                    "ROLE_" + role.getCode()
            ));
        });
        this.grantedAuthorities = grantedAuthorities;
        this.roles = authorities;
        return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
