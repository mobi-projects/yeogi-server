package com.example.yeogiserver.event.recommand.domain;

import com.example.yeogiserver.event.recommand.coverter.JsonConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Recommand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    @Convert(converter = JsonConverter.class)
    private Map<String,Integer> theme;

    @Convert(converter = JsonConverter.class)
    private Map<String,Integer> country;

    public static Recommand of(Long memberId, Map<String, Integer> theme, Map<String, Integer> country) {
        return Recommand.builder()
                .memberId(memberId)
                .theme(theme)
                .country(country)
                .build();
    }
}
