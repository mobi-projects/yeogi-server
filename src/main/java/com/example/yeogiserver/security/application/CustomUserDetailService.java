package com.example.yeogiserver.security.application;

import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.repository.MemberJpaRepository;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import com.example.yeogiserver.common.exception.customException;
import com.example.yeogiserver.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberJpaRepository.findByEmail(email)
                .map(this::createUserDetails)
                .orElseThrow(() -> new customException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private UserDetails createUserDetails(Member member) {
        return CustomUserDetails.of(member);
    }
}
