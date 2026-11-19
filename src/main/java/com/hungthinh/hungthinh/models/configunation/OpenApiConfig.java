package com.hungthinh.hungthinh.models.configunation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
//import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
// import io.swagger.v3.oas.models.security.SecurityRequirement;
// import io.swagger.v3.oas.models.security.SecurityScheme;
// import io.swagger.v3.oas.models.Components;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {

        String securitySchemeName = "Bearer Token";

        return new OpenAPI()
                .info(new Info()
                        .title("Hung Thinh Apartment Information Management System API")
                        .description("Hệ thống thông tin quản lý Chung cư Hưng Thịnh - API Documentation")
                        .version("v1.0"))

                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Development Server")
                ))

                // Cấu hình JWT Authentication
                .components(new Components()
                        .addSecuritySchemes(
                                securitySchemeName,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Nhập JWT Access Token để xác thực")
                        )
                )

                // Áp dụng JWT Authentication cho API
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(securitySchemeName)
                );
    }
}