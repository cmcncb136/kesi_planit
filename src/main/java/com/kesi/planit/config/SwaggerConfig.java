package com.kesi.planit.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .components(new Components())
                .info(appInfo());
    }

    private Info appInfo() {
        return new Info()
                .title("Kesi Planit")
                .description("Kesi Planit REST API")
                .version("1.0");
    }
}
