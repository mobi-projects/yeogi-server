package com.example.yeogiserver.mypage.presentation;

import com.example.yeogiserver.mypage.application.PinService;
import com.example.yeogiserver.mypage.application.dto.PinRequestDto;
import com.example.yeogiserver.mypage.application.dto.PinResponseDto;
import com.example.yeogiserver.mypage.application.dto.PinResponseWrapperDto;
import com.example.yeogiserver.mypage.domain.Pin;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PinController {
    private final PinService pinService;

    @PostMapping("/pin")
    public PinResponseDto addPin(@RequestBody PinRequestDto pin,@AuthenticationPrincipal CustomUserDetails userDetails) {
        return pinService.addPin(pin,userDetails);
    }

    @DeleteMapping("/pin/{pinId}")
    public void deletePin(@PathVariable Long pinId,@AuthenticationPrincipal CustomUserDetails userDetails) {
        pinService.deletePin(pinId);
    }
    @GetMapping("/pins")
    public PinResponseWrapperDto getPins(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return pinService.getPins(userDetails);
    }
}
