package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PhantomMask API")
                        .version("1.0")
                        .description("API documentation for PhantomMask application"))
                .servers(Arrays.asList(
                        new Server().url("http://localhost:8082").description("本地伺服器"),
                        new Server().url("https://api.hackdog.tw").description("生產環境")
                ));
    }
}
