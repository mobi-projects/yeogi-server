package com.example.yeogiserver.member.application;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.yeogiserver.common.exception.CustomException;
import com.example.yeogiserver.common.exception.ErrorCode;
import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.dto.MemberDto;
import com.example.yeogiserver.member.dto.MemberResponseDto;
import com.example.yeogiserver.member.dto.SignupMember;
import com.example.yeogiserver.member.dto.SignupRequestDto;
import com.example.yeogiserver.member.repository.DefaultMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final DefaultMemberRepository memberRepository;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

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

    public String updateProfileImage(Member member, MultipartFile image) {
        String uploadImageUrl = uploadImage(image);
        deleteImage(member.getProfile());
        member.setProfile(uploadImageUrl);
        return uploadImageUrl;
    }

    public String updateBanner(Member member, MultipartFile image) {
        String uploadImageUrl = uploadImage(image);
        deleteImage(member.getBanner());
        member.setBanner(uploadImageUrl);
        return uploadImageUrl;
    }

    public boolean checkExists(MemberDto memberDto) {
        return memberRepository.existsByNickname(memberDto.getNickname());
    }

    private String uploadImage(MultipartFile image){
        String originalFilename = image.getOriginalFilename();
        String newFilename = UUID.randomUUID() + originalFilename;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(image.getContentType());
        metadata.setContentLength(image.getSize());
        try {
            amazonS3.putObject(bucketName , newFilename , image.getInputStream() , metadata);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return amazonS3.getUrl(bucketName , newFilename).toString();
    }

    private void deleteImage(String imagePath) {
        if(imagePath == null || imagePath.isEmpty()) return;
        try {
            String[] urlParts = imagePath.split("/");

            amazonS3.deleteObject(bucketName , urlParts[3]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public MemberResponseDto getMember(String email, Long memberId) {
        if (memberId == null){
            Member member = memberRepository.findByEmail(email).orElseThrow();
            return MemberResponseDto.of(member);
        }

        Member member= memberRepository.findById(memberId).orElseThrow();
        return MemberResponseDto.of(member);
    }
}
