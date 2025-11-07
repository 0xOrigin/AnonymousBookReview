package io.github._0xorigin.anonymousbookreview.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                    new Info()
                    .title("Anonymous Book Review API")
                    .description("A REST API for anonymous book reviews and ratings")
                    .version("1.0.0")
                    .contact(
                        new Contact()
                            .name("Ahmed Ezzat Nasr")
                    )
                )
                .servers(
                    List.of(
                        new Server()
                        .url("http://localhost:8080")
                        .description("Development server")
                    )
                );
    }
}
