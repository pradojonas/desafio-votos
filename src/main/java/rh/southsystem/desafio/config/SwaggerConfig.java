package rh.southsystem.desafio.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket desafiotApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                                                      .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                                                      .paths(PathSelectors.any())
                                                      .build()
                                                      .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfo("Sistema de votação baseado em cooperativismo",
                                      "Desafio proposto para processo seletivo SouthSystem.",
                                      "1.0",
                                      "Terms of Service",
                                      new Contact("Jonas Prado Soares",
                                                  "https://github.com/pradojonas/",
                                                  "pradojonas64@gmail.com"),
                                      "Apache License Version 2.0",
                                      "https://www.apache.org/licesen.html",
                                      new ArrayList<VendorExtension>());
        return apiInfo;
    }

}