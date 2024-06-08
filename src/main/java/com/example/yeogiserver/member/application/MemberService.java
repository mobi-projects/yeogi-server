package com.example.yeogiserver.member.application;

import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.repository.DefaultMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final DefaultMemberRepository memberRepository;

    public void signup(Member member) {
        Member signmember = member.of(member.getEmail(), passwordEncoder.encode(member.getPassword()), member.getNickName(), member.getGender(), member.getBirthday());
        memberRepository.save(signmember);
    }

}
