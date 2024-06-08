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

    private CustomUserDetails(String email , Role role) {
        this.email = email;
        this.role = role;
    }

    public static CustomUserDetails of(Member member) {
        return new CustomUserDetails(member);
    }

    public static CustomUserDetails of(String email , Role role) {
        return new CustomUserDetails(email , role);
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
