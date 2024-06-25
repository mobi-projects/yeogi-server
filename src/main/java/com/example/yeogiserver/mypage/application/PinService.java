package com.example.yeogiserver.mypage.application;

import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.repository.DefaultMemberRepository;
import com.example.yeogiserver.mypage.application.dto.PinRequestDto;
import com.example.yeogiserver.mypage.application.dto.PinResponseDto;
import com.example.yeogiserver.mypage.application.dto.PinResponseWrapperDto;
import com.example.yeogiserver.mypage.domain.Pin;
import com.example.yeogiserver.mypage.domain.PinRepository;
import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.post.domain.PostRepository;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PinService {

    private final PinRepository pinRepository;
    private final DefaultMemberRepository memberRepository;
    private final PostRepository postRepository;

    public PinResponseDto addPin(PinRequestDto pinRequestDto, CustomUserDetails userDetails) {
        Member member = memberRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member email"));
        Post post = postRepository.findById(pinRequestDto.postId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));

        if(pinRepository.isExistPin(post.getId(),member.getEmail())) new IllegalArgumentException("Exist Pin");



        return PinResponseDto.of(pinRepository.save(Pin.of(pinRequestDto.x(), pinRequestDto.y(), member,post)));
    }
    public void deletePin(Long id){
        pinRepository.delete(id);
    }
    public PinResponseWrapperDto getPins(CustomUserDetails userDetails) {
        return PinResponseWrapperDto.of(pinRepository.getPins(userDetails.getEmail()));
    }
}
