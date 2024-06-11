package com.example.yeogiserver.security.domain;

import com.example.yeogiserver.member.domain.Member;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class CustomOAuth2User implements OAuth2User , Serializable {

    private Member member;
    private Map<String, Object> attributes;
    private List<GrantedAuthority> authorities;

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return member.getEmail();
    }

    public Member getMember() {
        return member;
    }
}
