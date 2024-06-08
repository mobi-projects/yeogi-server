package com.example.yeogiserver.security.domain;

import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.domain.Role;
import com.example.yeogiserver.security.config.CustomAuthorityUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@NoArgsConstructor
public class CustomUserDetails extends Member implements UserDetails {

    private Long id;
    private String email;
    private Role role;
    private String password;

    private CustomUserDetails(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.role = member.getRole();
    }

    public static CustomUserDetails of(Member member) {
        return new CustomUserDetails(member);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return CustomAuthorityUtils.createAuthorities(role);
    }

    @Override
    public String getUsername() {
        return this.email;
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
