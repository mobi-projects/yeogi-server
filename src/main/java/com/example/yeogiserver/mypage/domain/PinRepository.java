package com.example.yeogiserver.mypage.domain;

import java.util.List;

public interface PinRepository {

    Pin save(Pin pin);
    void delete(Long id);

    Boolean isExistPin(Long postId, String email);

    List<Pin> getPins(String email);
}
