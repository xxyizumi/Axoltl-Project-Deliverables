package com.example.springboot3advanced;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching // 追加: キャッシュ機能を有効化
@EnableAsync // 追加: 非同期処理を有効化
public class Springboot3AdvancedApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot3AdvancedApplication.class, args);
	}

}
