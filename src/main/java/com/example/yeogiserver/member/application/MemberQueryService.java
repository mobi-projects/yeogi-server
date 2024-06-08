package com.example.yeogiserver.member.application;

import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.repository.DefaultMemberRepository;
import com.example.yeogiserver.common.exception.customException;
import com.example.yeogiserver.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryService {

    private final DefaultMemberRepository memberRepository;

    public Member findMember(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new customException(ErrorCode.MEMBER_NOT_FOUND));
    }

}
