package com.example.yeogiserver.post.application.dto.request;

import com.example.yeogiserver.post.domain.Theme;

import java.time.LocalDateTime;
import java.util.List;

public record PostUpdateRequest(
        String continent,
        String region,
        LocalDateTime tripStartDate,
        LocalDateTime tripEndDate,
        String title,
        String content,
        List<Theme> themeList,
        List<MemoUpdateRequestDto> memos,
        String address
) {
}
