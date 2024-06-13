package com.example.yeogiserver.mypage.application;

import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.repository.DefaultMemberRepository;
import com.example.yeogiserver.mypage.application.dto.PinRequestDto;
import com.example.yeogiserver.mypage.application.dto.PinResponseDto;
import com.example.yeogiserver.mypage.domain.Pin;
import com.example.yeogiserver.mypage.domain.PinRepository;
import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.post.domain.PostRepository;
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

    public void addPin(PinRequestDto pinRequestDto) {
        Member member = memberRepository.findByEmail(pinRequestDto.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member email"));
        Post post = postRepository.findById(pinRequestDto.postId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));

        if(pinRepository.isExistPin(post.getId(),member.getEmail())) new IllegalArgumentException("Exist Pin");



        pinRepository.save(Pin.of(pinRequestDto.x(), pinRequestDto.y(), member,post));
    }
    public void deletePin(Long id){
        pinRepository.delete(id);
    }
    public PinResponseDto getPins(String email) {
        return PinResponseDto.of(pinRepository.getPins(email));
    }
}
