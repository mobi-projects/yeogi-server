package com.example.yeogiserver.member.application;


import com.example.yeogiserver.common.application.S3Service;
import com.example.yeogiserver.common.exception.CustomException;
import com.example.yeogiserver.common.exception.ErrorCode;
import com.example.yeogiserver.member.domain.Keyword;
import com.example.yeogiserver.member.domain.KeywordRepository;
import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.domain.MemberRepository;
import com.example.yeogiserver.member.dto.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final KeywordRepository keywordRepository;
    private final S3Service s3Service;

    public SignupMember.Response simpleSignup(SignupMember.Request member) {
        Member signmember = Member.of(member.getEmail(), passwordEncoder.encode(member.getPassword()), member.getNickname(), member.getAgeRange() , member.getProfile() , null , null , member.getGender());
        Member saveMember = memberRepository.save(signmember);
        return new SignupMember.Response(saveMember.getId(), saveMember.getEmail() , saveMember.getNickname());
    }

    public MemberResponseDto signup(SignupRequestDto signupRequestDto){
        Member member = memberRepository.findById(signupRequestDto.memberId()).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_REGISTRATION_FAIL)
        );
        member.completeSignup(signupRequestDto.nickname(), signupRequestDto.gender(), signupRequestDto.ageRange());
        return MemberResponseDto.of(member);
    }

    public Member update(MemberDto member) {
        Member findMember = memberRepository.findById(member.getId()).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        findMember.update(member);
        return findMember;
    }

    public void delete(String email) {
        memberRepository.delete(email);
    }

    public String updateProfileImage(Long memberId, MultipartFile image) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
        );
        String uploadImageUrl = s3Service.uploadImage(image);
        s3Service.deleteImage(member.getProfile());
        member.setProfile(uploadImageUrl);
        return uploadImageUrl;
    }

    public String updateBanner(Long memberId, MultipartFile image) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
        );
        String uploadImageUrl = s3Service.uploadImage(image);
        s3Service.deleteImage(member.getBanner());
        member.setBanner(uploadImageUrl);
        return uploadImageUrl;
    }

    public MemberEmailCheckResponseDto checkExists(String nickname) {
        return new MemberEmailCheckResponseDto(memberRepository.existsByNickname(nickname));
    }

    public MemberResponseDto getMember(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
        );
        return MemberResponseDto.of(member);
    }

    public MemberResponseDto getMyInfo(Long memberId) {
        Member member= memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
        );
        return MemberResponseDto.of(member);
    }

    public MemberResponseDto updateKeyword(Long memberId , List<Keyword> keywordList) {
        if (keywordRepository.existsByMember(memberId)) {
            keywordRepository.deleteByMember(memberId);
        }

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
        );

        keywordList.forEach(keyword -> {
            keyword.setMember(member);
            keywordRepository.save(keyword);
        });

        member.setKeywordList(keywordList);

        return MemberResponseDto.of(member);
    }

    public void updateIsFirst(TestRequestDto testRequestDto) {
        Member member= memberRepository.findById(testRequestDto.memberId()).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
        );

        member.setIsFirstForTest(testRequestDto.isFirst());
    }
}
