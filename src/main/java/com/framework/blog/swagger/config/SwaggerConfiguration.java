package com.framework.blog.swagger.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket api(){
        Predicate<RequestHandler> basePackage = RequestHandlerSelectors.basePackage("com.framework.blog");
        Predicate<String> apiUrls = PathSelectors.ant("/v1/**");
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(basePackage)
                .paths(apiUrls)
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "FrameworkBlog",
                "API RestFull to create a blog",
                "v1",
                "Terms of use",
                new Contact("Gabriel Faria", "https://gabrielfaria23.github.io/gabriel_fariaportfolio/", "gabrielnunesfariapta@hotmail.com"),
                "API Licenses", "URL of API Licenses", Collections.emptyList()
        );
    }

    @Bean
    public LinkDiscoverers discoverers() {
        List<LinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new CollectionJsonLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));

    }

}
