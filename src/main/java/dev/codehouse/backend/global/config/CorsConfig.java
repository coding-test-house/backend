package dev.codehouse.backend.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
          @Override
          public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
              .allowedOrigins("http://localhost:3000")
<<<<<<< HEAD
              .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
=======
              .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
>>>>>>> 8c5daa7d6f0cbca42a1cde20dae36cfb80bce166
              .allowCredentials(true);
          }
        };
    }
}
