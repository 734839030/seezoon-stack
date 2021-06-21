package com.seezoon.user.modules.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.seezoon.user.modules.security.dto.UserInfo;

import lombok.RequiredArgsConstructor;

/**
 * @author hdf
 */
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = -1;

    private final UserInfo userInfo;
    private final String username;
    private final String passowrd;
    private boolean locked;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
