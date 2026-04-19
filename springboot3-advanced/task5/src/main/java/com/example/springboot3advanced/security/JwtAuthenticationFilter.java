package com.example.springboot3advanced.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        
        // 1. リクエストヘッダーから "Authorization" を取得
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 2. ヘッダーがない、または "Bearer " で始まらない場合は、このフィルターは何もしない
        //    (次のフィルターへパスする -> ログイン画面へのアクセスなどはここでスルーされる)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. トークン部分を抽出 ("Bearer " の後ろ)
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);

        // 4. ユーザー名が取得でき、かつ現在のコンテキストで未認証の場合のみ処理
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // DBからユーザー情報をロード
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // 5. トークンの有効性を検証
            if (jwtService.isTokenValid(jwt, userDetails)) {
                
                // 6. 認証トークン（通行手形）を作成
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 7. SecurityContextにセット（これで「ログイン済み」とみなされる）
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        // 8. 次のフィルターへ進む
        filterChain.doFilter(request, response);
    }
}
