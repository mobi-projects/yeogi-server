package com.example.yeogiserver.mypage.repository;

import com.example.yeogiserver.mypage.domain.Pin;
import com.example.yeogiserver.mypage.domain.PinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class DefaultPinRepository implements PinRepository {

    private final JpaPinRepository jpaPinRepository;
    @Override
    public void save(Pin pin) {
        jpaPinRepository.save(pin);
    }

    @Override
    public void delete(Long id) {
        jpaPinRepository.deleteById(id);
    }

    @Override
    public Boolean isExistPin(Long postId, String email) {
        return jpaPinRepository.existsByPostIdAndMemberEmail(postId, email);
    }

    @Override
    public List<Pin> getPins(String email) {
        return jpaPinRepository.findByMemberEmail(email);
    }
}
