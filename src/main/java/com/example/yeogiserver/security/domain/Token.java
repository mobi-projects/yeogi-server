package com.example.yeogiserver.security.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.redis.core.index.Indexed;

@Data
@Builder
public class Token {

    private final String grantType;
    private final String authorizationType;
    private final String accessToken;
    private final String refreshToken;
    private final Long accessTokenExpiresIn;

}
