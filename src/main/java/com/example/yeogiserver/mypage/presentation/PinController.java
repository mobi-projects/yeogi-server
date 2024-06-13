package com.example.yeogiserver.mypage.presentation;

import com.example.yeogiserver.mypage.application.PinService;
import com.example.yeogiserver.mypage.application.dto.PinRequestDto;
import com.example.yeogiserver.mypage.application.dto.PinResponseDto;
import com.example.yeogiserver.mypage.domain.Pin;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PinController {
    private final PinService pinService;

    @PostMapping("/pin")
    public void addPin(@RequestBody PinRequestDto pin) {
        pinService.addPin(pin);
    }

    @DeleteMapping("/pin/{pinId}")
    public void deletePin(@PathVariable Long pinId) {
        pinService.deletePin(pinId);
    }
    @GetMapping("/pins/{email}")
    public PinResponseDto getPins(@PathVariable String email) {
        return pinService.getPins(email);
    }
}
