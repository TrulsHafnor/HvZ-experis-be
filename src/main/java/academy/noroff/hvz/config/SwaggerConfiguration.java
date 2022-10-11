package academy.noroff.hvz.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;

/*@OpenAPIDefinition(info = @Info(title = "HvZ", description = "Humans VS Zombies API", version = "0.1.0"))
@SecurityScheme(name = "auth_implicit", type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(authorizationCode = @OAuthFlow(authorizationUrl = "${spring.security.oauth2.resourceserver.jwt.issuer-uri}protocol/openid-connect/auth", tokenUrl = "${spring.security.oauth2.resourceserver.jwt.issuer-uri}protocol/openid-connect/token", scopes = {
                @OAuthScope(name = "openid profile email", description = "OpenID Connect Endpoints")
        }))
)
public class SwaggerConfiguration {
}*/

@Configuration
public class SwaggerConfiguration {
        @Bean
        public OpenAPI customizeOpenAPI() {
                final String securitySchemeName = "bearerAuth";
                return new OpenAPI()
                        .addSecurityItem(new SecurityRequirement()
                                .addList(securitySchemeName))
                        .components(new Components()
                                .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
        }
}




