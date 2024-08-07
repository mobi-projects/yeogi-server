package com.example.yeogiserver.mypage.presentation;

import com.example.yeogiserver.mypage.application.PinService;
import com.example.yeogiserver.mypage.application.dto.PinFixRequestDto;
import com.example.yeogiserver.mypage.application.dto.PinRequestDto;
import com.example.yeogiserver.mypage.application.dto.PinResponseDto;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PinController {

    private final PinService pinService;

    @PostMapping("/pins")
    public PinResponseDto addPin(@RequestBody PinRequestDto pin,@AuthenticationPrincipal CustomUserDetails userDetails) {
        return pinService.addPin(pin,userDetails);
    }

    @PutMapping("/pins/{pinId}")
    public void fixPin(@PathVariable Long pinId, @RequestBody PinFixRequestDto pinRequestDto, @AuthenticationPrincipal CustomUserDetails userDetails){
        pinService.fixPin(pinId, pinRequestDto, userDetails.getId());
    }

    // 생성된 핀의 x,y 좌표를 찾아 꽂아주는 기능
    @DeleteMapping("/pins/{pinId}")
    public void deletePin(@PathVariable Long pinId,@AuthenticationPrincipal CustomUserDetails userDetails) {
        pinService.deletePin(pinId, userDetails.getId());
    }

    @GetMapping("/pins")
    public List<PinResponseDto> getPins(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return pinService.getPins(userDetails);
    }
}
