package com.example.yeogiserver.mypage.application;

import com.example.yeogiserver.common.exception.CustomException;
import com.example.yeogiserver.common.exception.ErrorCode;
import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.domain.MemberRepository;
import com.example.yeogiserver.mypage.application.dto.PinFixRequestDto;
import com.example.yeogiserver.mypage.application.dto.PinRequestDto;
import com.example.yeogiserver.mypage.application.dto.PinResponseDto;
import com.example.yeogiserver.mypage.domain.Pin;
import com.example.yeogiserver.mypage.domain.PinRepository;
import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.post.domain.PostRepository;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class PinService {

    private final PinRepository pinRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public PinResponseDto addPin(PinRequestDto pinRequestDto, CustomUserDetails userDetails) {
        Member member = memberRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member email"));

        Post post = postRepository.findById(pinRequestDto.postId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));

        boolean isPinExists = pinRepository.isExistPin(post.getId(), member.getEmail());

        if (isPinExists) {
            throw new IllegalArgumentException("Exist Pin");
        }

        Pin pin = pinRepository.save(Pin.of(member, post));
        return PinResponseDto.of(pin);
    }

    public void fixPin(Long pinId, PinFixRequestDto pinFixRequestDto, Long memberId) {
        Pin pin = getPin(pinId);
        if (!Objects.equals(pin.getMember().getId(), memberId)) {
            throw new CustomException(ErrorCode.NOT_MY_PIN);
        }

        pin.fixPin(pinFixRequestDto.x(), pinFixRequestDto.y());
    }

    public void deletePin(Long id, Long memberId) {
        Pin pin = getPin(id);

        if (!Objects.equals(pin.getMember().getId(), memberId)) {
            throw new CustomException(ErrorCode.NOT_MY_PIN);
        }

        pinRepository.delete(pin.getId());
    }

    private Pin getPin(Long id) {
        return pinRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.PIN_NOT_FOUND));
    }

    public List<PinResponseDto> getPins(CustomUserDetails userDetails) {
        List<Pin> pins = pinRepository.getPins(userDetails.getEmail());
        return pins.stream().map(PinResponseDto::of).toList();
    }
}
