package com.example.yeogiserver.member.presentation;

import com.example.yeogiserver.common.exception.CustomException;
import com.example.yeogiserver.common.exception.ErrorCode;
import com.example.yeogiserver.common.resolver.LoginMember;
import com.example.yeogiserver.member.application.MemberQueryService;
import com.example.yeogiserver.member.application.MemberService;
import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.dto.MemberDto;
import com.example.yeogiserver.member.dto.SignupMember;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    // TODO : 필요에 따라 컨트롤러도 분리한다.

    private final MemberService memberService;

    private final MemberQueryService memberQueryService;

    @PostMapping()
    public SignupMember.Response signup(@RequestBody SignupMember.Request member) {
        if(memberQueryService.existsMemberEmail(member)){
            throw new CustomException(ErrorCode.MEMBER_EMAIL_ALREADY_EXISTS);
        }
        return memberService.signup(member);
    }

    @GetMapping()
    public MemberDto getMember(@LoginMember Member member) {
        return MemberDto.of(member);
    }

    @PutMapping()
    public MemberDto updateMember(@RequestBody MemberDto member) {
        Member updateMember = memberService.update(member);
        return MemberDto.of(updateMember);
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
