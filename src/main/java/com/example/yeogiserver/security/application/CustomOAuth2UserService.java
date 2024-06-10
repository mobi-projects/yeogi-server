package com.example.yeogiserver.security.application;

import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.repository.DefaultMemberRepository;
import com.example.yeogiserver.security.config.CustomAuthorityUtils;
import com.example.yeogiserver.security.domain.CustomOAuth2User;
import com.example.yeogiserver.security.domain.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final DefaultMemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Map<String, Object> attributes = super.loadUser(userRequest).getAttributes();

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(registrationId, attributes);

        Member member = saveOrUpdate(oAuth2UserInfo);

        List<GrantedAuthority> authorities = CustomAuthorityUtils.createAuthorities(member.getRole());

        return new CustomOAuth2User(member, attributes , authorities);
    }

    private Member saveOrUpdate(OAuth2UserInfo oAuth2UserInfo) {
        Member member = memberRepository.findByEmail(oAuth2UserInfo.getEmail())
                .orElse(oAuth2UserInfo.toEntity());
        return memberRepository.save(member);
    }
}
