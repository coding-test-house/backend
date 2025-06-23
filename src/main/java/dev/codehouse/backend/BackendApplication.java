package dev.codehouse.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableScheduling
public class BackendApplication {
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**") // 모든 URL에 대해
						.allowedOrigins("*") // 모든 출처 허용
						.allowedMethods("*") // GET, POST, PUT, DELETE 등 모든 메서드 허용
						.allowedHeaders("*") // 모든 헤더 허용
						.allowCredentials(false); // 인증 정보 포함 여부 (true면 allowedOrigins는 "*" 사용 못 함)
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
