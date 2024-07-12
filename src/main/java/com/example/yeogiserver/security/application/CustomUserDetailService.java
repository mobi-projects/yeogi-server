package com.example.yeogiserver.security.application;

import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.repository.MemberJpaRepository;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberJpaRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("user not found")
        );
        return createUserDetails(member);
    }

    private CustomUserDetails createUserDetails(Member member) {
        return CustomUserDetails.of(member);
    }
}
