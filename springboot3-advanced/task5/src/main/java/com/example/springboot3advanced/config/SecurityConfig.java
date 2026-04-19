package com.example.springboot3advanced.config;

import com.example.springboot3advanced.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CSRF無効化: ステートレスなREST APIでは通常不要（Cookieを使わないため）
            .csrf(csrf -> csrf.disable())
            
            // ★ここが「セキュリティ対象外」の設定
            .authorizeHttpRequests(auth -> auth
                // "/api/v1/auth/" から始まるURLは認証なしでアクセス許可 (White List)
                .requestMatchers("/api/v1/auth/**").permitAll()
                
                // それ以外はすべて認証が必要
                .anyRequest().authenticated()
            )
            
            // セッション管理: ステートレス（サーバーにセッションを作らない）に設定
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            .authenticationProvider(authenticationProvider)
            // 標準のUsernamePasswordAuthenticationFilterの「前」にJWTフィルターを実行
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
