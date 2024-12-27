package com.beehive.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Open API Swagger UI configuration.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    GroupedOpenApi publicApi() {
        return GroupedOpenApi
                .builder()
                .group("PUBLIC-APIs")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info().title("BEEHIVE-API").version("1.0"))
                .addSecurityItem(new SecurityRequirement().addList("cookieAuth"))
                .components(new Components()
                        .addSecuritySchemes("cookieAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.COOKIE)
                                .name("token")
                        ));
    }
}
