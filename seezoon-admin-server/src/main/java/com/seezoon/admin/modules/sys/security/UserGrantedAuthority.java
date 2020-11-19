package com.seezoon.admin.modules.sys.security;

import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

/**
 * 默认放入如果是角色，要求是有ROLE_ 前缀，当然可以自己定义
 *
 * <code>
 *     基于注解的权限控制，里面如果传入的是角色，需要加前缀ROLE_
 *     &#64;Secured({"ROLE_ADMIN"}) 非常不建议使用，不是很灵活
 *      &#64;PreAuthorize("hasAuthority('ROLE_admin')")
 *      &#64;PreAuthorize("hasRole('admin')")
 * </code>
 *
 * @author hdf
 */
public class UserGrantedAuthority implements GrantedAuthority {
    private static final String DEFAULT_ROLE_PREFIX = "ROLE_";

    private static final long serialVersionUID = 1L;
    private String authorityName;

    public UserGrantedAuthority(String authorityName) {
        this(authorityName, false);
    }

    public UserGrantedAuthority(String authorityName, boolean isRole) {
        Assert.hasText(authorityName, "A granted authority textual representation is required");
        this.authorityName = isRole ? DEFAULT_ROLE_PREFIX + authorityName : authorityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserGrantedAuthority that = (UserGrantedAuthority)o;
        return authorityName.equals(that.authorityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorityName);
    }

    @Override
    public String getAuthority() {
        return authorityName;
    }

}
