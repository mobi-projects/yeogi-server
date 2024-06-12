package com.example.yeogiserver.member.presentation;

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
        return memberService.signup(member);
    }

    @GetMapping()
    public MemberDto getMember(@AuthenticationPrincipal CustomUserDetails member) {
        Member findMember = memberQueryService.findMember(member.getEmail());
        return MemberDto.of(findMember);
    }

    @PutMapping()
    public MemberDto updateMember(@RequestBody MemberDto member) {
        Member updateMember = memberService.update(member);
        return MemberDto.of(updateMember);
    }

    @DeleteMapping()
    public ResponseEntity deleteMember(@AuthenticationPrincipal CustomUserDetails member) {
        memberService.delete(member.getEmail());

        return new ResponseEntity("탈퇴 되었습니다." , HttpStatus.OK);
    }

    @GetMapping("test")
    public String test() {
        return "TEST";
    }

}
