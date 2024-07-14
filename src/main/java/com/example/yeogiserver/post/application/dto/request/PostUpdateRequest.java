package com.example.yeogiserver.post.application.dto.request;

import com.example.yeogiserver.post.domain.Theme;

import java.time.LocalDateTime;
import java.util.List;

public record PostUpdateRequest(
        LocalDateTime tripStartDate,
        LocalDateTime tripEndDate,
        String title,
        String content,
        String continent,
        String country,
        List<Theme> themeList,
        List<MemoUpdateRequestDto> memos,
        String address
) {
}
