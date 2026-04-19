package com.example.springboot3advanced.auth;

import com.example.springboot3advanced.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void shouldRegisterUser() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                .firstname("Ali")
                .lastname("Bouali")
                .email("ali@mail.com")
                .password("password")
                .build();

        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAuthenticateUser() throws Exception {
        // まずユーザーを登録
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstname("Ali")
                .lastname("Bouali")
                .email("ali@mail.com")
                .password("password")
                .build();

        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk());

        // ログインリクエスト
        AuthenticationRequest authRequest = AuthenticationRequest.builder()
                .email("ali@mail.com")
                .password("password")
                .build();

        mockMvc.perform(post("/api/v1/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFailAuthenticationWithWrongPassword() throws Exception {

        // ユーザー登録...
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstname("Tarou")
                .lastname("Tanaka")
                .email("tanaka@mail.com")
                .password("password")
                .build();

        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk());


        // 間違ったパスワードでリクエスト
        AuthenticationRequest authRequest = AuthenticationRequest.builder()
                .email("ali@mail.com")
                .password("wrong-password")
                .build();

        mockMvc.perform(post("/api/v1/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isForbidden()); // または isUnauthorized()
    }
}
