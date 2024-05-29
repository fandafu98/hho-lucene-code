package com.hho.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    private static final List<String> WHITE_URLS = new ArrayList<>();

    static {
        WHITE_URLS.add("/error");
        WHITE_URLS.add("/csrf");
        WHITE_URLS.add("/webjars/**");
        WHITE_URLS.add("/swagger-resources/**");
        WHITE_URLS.add("/favicon.ico");
        WHITE_URLS.add("/swagger-ui.html");
        WHITE_URLS.add("/doc.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

}
