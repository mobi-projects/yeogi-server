package com.example.yeogiserver.security.config;

import com.example.yeogiserver.member.domain.Role;
import com.example.yeogiserver.common.exception.customException;
import com.example.yeogiserver.common.exception.ErrorCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static com.example.yeogiserver.member.domain.Role.ADMIN;
import static com.example.yeogiserver.member.domain.Role.USER;

public class CustomAuthorityUtils {

    public static List<GrantedAuthority> createAuthorities(Role role) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    public static void verifiedRole(String role) {
        if(role == null) {
            throw new customException(ErrorCode.MEMBER_ROLE_DOES_NOT_EXISTS);
        }else if (!role.equals(USER) && !role.equals(ADMIN)) {
            throw new customException(ErrorCode.MEMBER_ROLE_INVALID);
        }
    }
}
