package com.example.cardatabase;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 구성 관련 클래스라는 뜻
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI carDatabaseOpenApi() {
        // 굳이 따지자면 메서드
        return new OpenAPI()
                .info(new Info()
                        .title("Car REST API")
                        .description("My car Stock")
                        .version("1.0")
                );
    }
}
