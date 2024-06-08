package com.example.yeogiserver.common.config;

import com.example.yeogiserver.common.application.RedisService;
import com.example.yeogiserver.member.application.MemberQueryService;
import com.example.yeogiserver.security.filter.JwtAuthenticationFilter;
import com.example.yeogiserver.security.config.JwtTokenProvider;
import com.example.yeogiserver.security.handler.LoginFailureHandler;
import com.example.yeogiserver.security.handler.LoginSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberQueryService memberQueryService;
    private final RedisService redisService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http.sessionManagement(sessionManger -> sessionManger.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.exceptionHandling((exceptionHandling) -> exceptionHandling
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                        log.warn("Unauthorized error happend: {}" , authException.getMessage());
                    }
                })
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                        log.warn("Forbidden error happend: {}" , accessDeniedException.getMessage());
                    }
                })
        );

        http.authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll());

        http.with(new CustomFilterConfigurer() , CustomFilterConfigurer::build);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("Authorization");
        config.addExposedHeader("Refresh");
        config.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", config);
        return source;
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer , HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenProvider, memberQueryService , redisService);

            jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new LoginFailureHandler());

            builder.addFilter(jwtAuthenticationFilter);
            super.configure(builder);
        }

        public HttpSecurity build() {
            return getBuilder();
        }
    }
}
