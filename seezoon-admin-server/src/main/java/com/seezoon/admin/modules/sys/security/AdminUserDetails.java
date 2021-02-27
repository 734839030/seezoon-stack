package com.seezoon.admin.modules.sys.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.seezoon.admin.modules.sys.dto.UserInfo;

import lombok.RequiredArgsConstructor;

/**
 * @author hdf
 */
@RequiredArgsConstructor
public class AdminUserDetails implements UserDetails {

    private final UserInfo userInfo;
    private final String username;
    private final String passowrd;
    private final boolean locked;

    private List<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return passowrd;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

}
