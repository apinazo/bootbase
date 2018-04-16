package es.apinazo.bootbase.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()) // Document all endpoints.
//                .paths(PathSelectors.ant("/my-api/*")) // Document only those under this URL.
                .build()
                .apiInfo(apiInfo()); // Custom info, not needed.
    }


    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Bootbase REST API",
                "A nice API not from an ape.",
                "API TOS",
                "Terms of service",
                new Contact("Angel Pinazo", "angelpinazo.wordpress.com", "angelpinazo@gmail.com"),
                "License of API", "http://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList());
    }

}