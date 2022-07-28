package com.example.human.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI().info(new Info()
            .title("SwaggerConfig - Human Service ")
            .description("SwaggerConfig - Additional Attributes Open API")
            .version("SwaggerConfig - 3.0")
            .license(new License().name("SwaggerConfig - Apache 2.0").url("SwaggerConfig - http://springdoc.org"))
            .description("SwaggerConfig - SpringShop Wiki Documentation")
        );
    }


    @Bean
    public GroupedOpenApi api()
    {
        return GroupedOpenApi.builder()
            .group("SwaggerConfig - OpenApiControllers")
            .packagesToScan("com.example")
            .build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }


//    @GetMapping(value = {"/" + "documentation", "/" + "documentation" + "/"})
//    public String redirectSwaggerUrl() {
//        return "redirect:/" + "swagger-ui" + "/index.html";
//    }

}





