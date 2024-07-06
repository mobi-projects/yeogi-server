package com.example.yeogiserver.global.fixture;

import com.example.yeogiserver.member.domain.Gender;
import com.example.yeogiserver.member.domain.Member;

public class MemberFixture {
    public static Member TEST_MEMBER_FIXTURE = Member.of(
            "email",
            "pass",
            "nick",
            "asg",
            "pro",
            "mo",
            "ba",
            Gender.M
    );
}
