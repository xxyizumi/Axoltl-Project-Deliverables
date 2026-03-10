package com.example.thymeleafadvanced.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.thymeleafadvanced.security.CustomUserDetails;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // テスト用ユーザーデータ
    private static final Map<String, UserData> USERS = Map.of(
        "user", new UserData("password", "一般ユーザー", "user@example.com", List.of("ROLE_USER")),
        "manager", new UserData("password", "田中マネージャー", "manager@example.com", List.of("ROLE_USER", "ROLE_MANAGER")),
        "admin", new UserData("password", "管理者 太郎", "admin@example.com", List.of("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN"))
    );

    record UserData(String password, String displayName, String email, List<String> roles) {}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/login", "/register").permitAll()
                .requestMatchers("/demo/**").permitAll()  // デモページは全員アクセス可
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );
        
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        return username -> {
            UserData userData = USERS.get(username);
            if (userData == null) {
                throw new UsernameNotFoundException("User not found: " + username);
            }
            
            List<SimpleGrantedAuthority> authorities = userData.roles().stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
            DateTimeFormatter loginDateTimeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

            return new CustomUserDetails(
                username,
                encoder.encode(userData.password()),
                authorities,
                userData.displayName(),
                userData.email(),
                LocalDateTime.now().format(loginDateTimeFormat)
            );
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.fromHierarchy("ROLE_ADMIN > ROLE_MANAGER > ROLE_EDITOR > ROLE_USER");
    }
}
