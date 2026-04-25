package com.example.springboot3advanced.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Async // このメソッドは別スレッドで実行される
    public void sendWelcomeEmail(String toEmail) {
        log.info("Start sending email to {}", toEmail);
        
        try {
            // メール送信にかかる時間をシミュレート（5秒）
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        log.info("Email sent successfully to {}", toEmail);
    }
}
