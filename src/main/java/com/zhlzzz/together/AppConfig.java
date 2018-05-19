package com.zhlzzz.together;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhlzzz.together.controllers.ApiAuthenticationMethodArgumentResolver;
import com.zhlzzz.together.controllers.Slices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class AppConfig {

    @Configuration
    @RequiredArgsConstructor(onConstructor = @__(@Autowired))
    public static class WebConfigurer implements WebMvcConfigurer {

        private final ApplicationContext context;
        private final ObjectMapper objectMapper;

        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
            resolvers.add(new ApiAuthenticationMethodArgumentResolver());
            resolvers.add(new Slices.SliceIndicatorsMethodArgumentResolver(context));
        }

        @Override
        public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
            converters.add(0, new Slices.SliceHttpMessageConverter(objectMapper));
        }
    }

}
