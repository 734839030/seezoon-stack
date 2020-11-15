package com.seezoon.admin.modules.sys.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 用户加载逻辑
 *
 * @author hdf
 */
public class AdminUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminUserDetails adminUserDetails =
            new AdminUserDetails(username, new BCryptPasswordEncoder().encode("123"), false, null);
        return adminUserDetails;
    }
}
