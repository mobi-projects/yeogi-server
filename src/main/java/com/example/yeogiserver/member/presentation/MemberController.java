package com.example.yeogiserver.member.presentation;

import com.example.yeogiserver.member.application.MemberQueryService;
import com.example.yeogiserver.member.application.MemberService;
import com.example.yeogiserver.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    // TODO : 필요에 따라 컨트롤러도 분리한다.

    private final MemberService memberService;

    private final MemberQueryService memberQueryService;

    @RequestMapping("signup")
    public void signup(@RequestBody Member member) {
        memberService.signup(member);
    }

}
