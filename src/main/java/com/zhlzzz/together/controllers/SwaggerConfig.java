package com.zhlzzz.together.controllers;

import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@Profile({"dev", "test"})
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiList(TypeResolver typeResolver) {
        Docket apis = new Docket(DocumentationType.SWAGGER_2)
                .groupName("togethor-api")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                ;
        configGlobalResponseMessage(apis);
        configTags(apis);
        configAdditionalModels(apis, typeResolver);
        return apis;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Together RESTful APIs")
                .description("")
                .version("1.0")
                .build();
    }

    @Bean
    public UiConfiguration swaggerUiConfigiguration() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(true)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .validatorUrl(null)
                .build();
    }

    private void configGlobalResponseMessage(Docket api) {
        api.globalResponseMessage(RequestMethod.GET, globalResponseMessageForGet())
                .globalResponseMessage(RequestMethod.POST, globalResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, globalResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, globalResponseMessages());
    }

    private List<ResponseMessage> globalResponseMessages() {
        return Collections.emptyList();
    }

    private List<ResponseMessage> globalResponseMessageForGet() {
        List<ResponseMessage> all = globalResponseMessages();
        all.addAll(newArrayList(

        ));
        return all;
    }

    private void configTags(Docket api) {
        api.tags(
                new Tag("User", "用户api"),
                new Tag("UserLabel", "用户标签"),
                new Tag("Feedback", "意见反馈"),
                new Tag("Discuss", "评论-回复"),
                new Tag("Advert", "广告管理"),
                new Tag("Game","游戏")
        );
    }

    private void configAdditionalModels(Docket api, TypeResolver typeResolver) {
        api.additionalModels(
                typeResolver.resolve(ApiExceptionResponse.class)
        );
    }
}
