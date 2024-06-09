package com.example.yeogiserver.security.filter;

import com.example.yeogiserver.common.application.RedisService;
import com.example.yeogiserver.security.config.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter{

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    private static final List<String> EXCLUDE_URL =
            List.of("/",
                    "/h2",
                    "/member/signup",
                    "/auth/login",
                    "/auth/reissue");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String accessToken = jwtTokenProvider.resolveAccessToken(request);
            if(StringUtils.hasText(accessToken) && doNotLogout(accessToken) && jwtTokenProvider.validateToken(accessToken , response)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("# Token verification success!");
            }
        }catch (RuntimeException e) {
            throw e;
        }

        filterChain.doFilter(request , response);
    }

    private boolean doNotLogout(String accessToken) {
        String isLogout = redisService.getValue(accessToken);
        return isLogout.equals("false");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return EXCLUDE_URL.stream().anyMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath()));
    }

}
