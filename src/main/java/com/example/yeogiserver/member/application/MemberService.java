package com.example.yeogiserver.member.application;

import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.dto.SignupMember;
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

    public SignupMember.Response signup(SignupMember.Request member) {
        Member signmember = Member.of(member.getEmail(), passwordEncoder.encode(member.getPassword()), member.getNickname(), member.getAgeRange() , member.getProfile() , member.getGender());
        Member saveMember = memberRepository.save(signmember);
        return new SignupMember.Response(saveMember.getEmail() , saveMember.getNickname());
    }

}
