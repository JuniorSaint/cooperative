package br.com.cooperative.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

 @EnableJpaRepositories(basePackages = {"br.com.cooperative.repositories"})
 @EntityScan(basePackages = {"br.com.cooperative.models.entities"})
 @ComponentScan(basePackages = {"br.com.cooperative.controllers",
 		"br.com.cooperative.services", "br.com.cooperative.configs",
 		"br.com.cooperative.exceptions"
 })
@Configuration
public class GeralConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }

    //    @Bean
//    public PasswordEncoder getPasswordEncoder() {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        return encoder;
//    }
    @Bean
    public FreeMarkerViewResolver freemarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setCache(true);
        resolver.setPrefix("");
        resolver.setSuffix(".ftlh");
        return resolver;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API to manager application Proonto")
                        .version("v1")
                        .description("RESTful API develop with Java 11 and Spring Boot 2.7.2")
                        .termsOfService("https://www.idip.com.br")
                        .license(
                                new License()
                                        .name("Apache 2.0")
                                        .url("https://www.idip.com.br")
                        )
                );
    }

}
