package com.perez.tdduniversity.system;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI openApi() {
        Contact contact = new Contact();
        contact.setName("Jhoan Sebastián Pérez");
        contact.setEmail("jhoansebastianpa@ufps.edu.co");
        contact.setUrl("https://github.com/jhoanSebasPerez");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("TDD University API")
                .version("1.0")
                .contact(contact)
                .description("Simple API to manage a university to demonstrated TDD methodology")
                .license(mitLicense);

        return new OpenAPI().info(info);
    }
}
