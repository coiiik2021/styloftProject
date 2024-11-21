package org.sale.project.payment.banking;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import vn.payos.PayOS;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PayOsConfig implements WebMvcConfigurer {

    @Value("${payment.PAYOS.CLIENT_ID}")
    String clientId;

    @Value("${payment.PAYOS.API_KEY}")
    String apiKey;

    @Value("${payment.PAYOS.CHECKSUM_KEY}")
    String checksumKey;

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600); // Max age of the CORS pre-flight request
    }

    @Bean
    public PayOS payOS() {
        return new PayOS(clientId, apiKey, checksumKey);
    }



}
