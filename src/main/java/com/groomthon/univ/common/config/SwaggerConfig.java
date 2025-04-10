package com.groomthon.univ.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        Server localServer = new Server()
                .url("http://localhost:8080")
                .description("Univ study server");

        return new OpenAPI()
                .info(new Info()
                        .title("9roomthon-study")
                        .description("구름톤 유니브 스프링 스터디 REST API Document")
                        .version("1.0.0"))
                .components(new Components())
                .addServersItem(localServer);
    }
}
