package br.com.gabrielflorentino.api_boleto.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("API boleto")
                .description("API de pagamento de boletos")
                .contact(new Contact().name("Gabriel Florentino").email("contato.gabrielflorentino@gmail.com"))
                .version("1.0.0")
        );
    }
}
