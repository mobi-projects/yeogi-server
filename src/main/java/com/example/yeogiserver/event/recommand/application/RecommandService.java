package com.example.yeogiserver.event.recommand.application;

import com.example.yeogiserver.event.recommand.RecommandEvent;
import com.example.yeogiserver.event.recommand.domain.Recommand;
import com.example.yeogiserver.event.recommand.domain.RecommandRepository;
import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.post.domain.PostReadRepository;
import com.example.yeogiserver.post.domain.PostTheme;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommandService {

    private final RecommandRepository recommandRepository;
    private final PostReadRepository postReadRepository;

    @Transactional
    public void saveRecommand(RecommandEvent recommandEvent) {
        Optional<Recommand> recommandOptional = recommandRepository.findByMemberId(recommandEvent.getMemberId());
        Post post = postReadRepository.findById(recommandEvent.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("post not found"));

        if(recommandOptional.isPresent()) {
            Recommand recommand = recommandOptional.get();

            Map<String,Integer> jsonTheme = recommand.getTheme();
            Map<String,Integer> jsonCountry = recommand.getCountry();

            for (PostTheme theme : post.getPostThemeList()) {
                String key = String.valueOf(theme.getTheme());
                jsonTheme.put(key,jsonTheme.getOrDefault(key,0)+1);
            }

            jsonCountry.put(post.getCountry(), jsonCountry.getOrDefault(post.getCountry(),0)+1);
            recommand.setTheme(jsonTheme);
            recommand.setCountry(jsonCountry);

            recommandRepository.save(recommand);

        } else {

            Map<String,Integer> jsonTheme = new HashMap<>();
            Map<String,Integer> jsonCountry = new HashMap<>();

            for (PostTheme theme : post.getPostThemeList()) {
                String key = String.valueOf(theme.getTheme());
                jsonTheme.put(key,jsonTheme.getOrDefault(key,0)+1);
            }

            jsonCountry.put(post.getCountry(), jsonCountry.getOrDefault(post.getCountry(),0)+1);

            Recommand recommand = Recommand.of(recommandEvent.getMemberId(), jsonTheme,jsonCountry);
            recommandRepository.save(recommand);
        }
    }
}
