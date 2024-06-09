package com.example.yeogiserver.member.presentation;

import com.example.yeogiserver.member.application.MemberQueryService;
import com.example.yeogiserver.member.application.MemberService;
import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.dto.SignupMember;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    // TODO : 필요에 따라 컨트롤러도 분리한다.

    private final MemberService memberService;

    private final MemberQueryService memberQueryService;

    @PostMapping("signup")
    public SignupMember.Response signup(@RequestBody SignupMember.Request member) {
        return memberService.signup(member);
    }

    @GetMapping()
    public Member getMember(@AuthenticationPrincipal CustomUserDetails user) {
        String email = user.getEmail();
        return memberQueryService.findMember(email);
    }

    @GetMapping("test")
    public String test() {
        return "TEST";
    }

}
