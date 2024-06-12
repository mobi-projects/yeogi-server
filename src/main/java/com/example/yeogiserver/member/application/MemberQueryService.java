package com.example.yeogiserver.member.application;

import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.repository.DefaultMemberRepository;
import com.example.yeogiserver.common.exception.CustomException;
import com.example.yeogiserver.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryService {

    private final DefaultMemberRepository memberRepository;

    public Member findMember(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public List<Member> findAllByIds(List<Long> memberIds) {
        return memberRepository.findAllByIds(memberIds);
    }
}
