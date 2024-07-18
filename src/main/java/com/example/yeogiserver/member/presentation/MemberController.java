package com.example.yeogiserver.member.presentation;

import com.example.yeogiserver.common.exception.CustomException;
import com.example.yeogiserver.common.exception.ErrorCode;
import com.example.yeogiserver.common.resolver.LoginMember;
import com.example.yeogiserver.member.application.MemberQueryService;
import com.example.yeogiserver.member.application.MemberService;
import com.example.yeogiserver.member.domain.Keyword;
import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.dto.*;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "회원 관련 컨트롤러")
public class MemberController {

    private final MemberService memberService;

    private final MemberQueryService memberQueryService;

    @PostMapping()
    public SignupMember.Response signup(@RequestBody SignupMember.Request member) {
        if(memberQueryService.existsMemberEmail(member)){
            throw new CustomException(ErrorCode.MEMBER_EMAIL_ALREADY_EXISTS);
        }
        return memberService.simpleSignup(member);
    }

    @PostMapping("/signup")
    @Operation(description = "회원 간편가입 후, 일반 가입 API")
    public MemberResponseDto signup(@RequestBody SignupRequestDto signupRequestDto){
        return memberService.signup(signupRequestDto);
    }

    @GetMapping()
    @Operation(description = "회원 정보 GET API, email 에 해당하는 유저를 가져온다.")
    public MemberResponseDto getMember(@RequestParam String email){
        return memberService.getMember(email);
    }

    @GetMapping("/me")
    @Operation(description = "회원 정보 GET API, 토큰 정보에 해당하는 유저를 가져온다.")
    public MemberResponseDto getMyInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return memberService.getMyInfo(customUserDetails.getId());
    }

    @PutMapping("/isFirst/test")
    public void updateIsFirst(@RequestBody TestRequestDto testRequestDto){
        memberService.updateIsFirst(testRequestDto);
    }

    @PutMapping()
    public MemberDto updateMember(@RequestBody MemberDto member) {
        Member updateMember = memberService.update(member);
        return MemberDto.of(updateMember);
    }

    @PutMapping("profileImage")
    public String updateProfileImage(@LoginMember Member member , @RequestPart(name = "image") MultipartFile image){
        return memberService.updateProfileImage(member, image);
    }

    @PutMapping("banner")
    public String updateBanner(@LoginMember Member member , @RequestPart(name = "image") MultipartFile image){
        return memberService.updateBanner(member , image);
    }

    @PutMapping("keyword")
    public MemberResponseDto updateKeyword(@LoginMember Member member , @RequestBody List<Keyword> keywordList) {
        return memberService.updateKeyword(member.getId() , keywordList);
    }

    @GetMapping("/emails")
    public MemberEmailCheckResponseDto checkExists(@RequestParam String email) {
        return memberService.checkExists(email);
    }

    @DeleteMapping()
    public ResponseEntity deleteMember(@LoginMember Member member) {
        memberService.delete(member.getEmail());
        return new ResponseEntity("탈퇴 되었습니다." , HttpStatus.OK);
    }

    @GetMapping("test")
    public String test() {
        return "TEST";
    }

}
