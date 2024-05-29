

package com.hho.web.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/** * Description: swagger文档配置 * */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class Swagger2Config {

    @Value("${swagger2.enable}")
    private boolean swagger2Enable;

    @Bean
    public Docket createQrApiSystem() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swagger2Enable)
                .groupName("康康")
                .apiInfo(apiInfo("我的代码面试题"))
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hho.web.api"))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo(String title) {
        return new ApiInfoBuilder().title(title).description("郑延康 面试两氢一氧的代码面试题").termsOfServiceUrl("https://github.com/fandafu98/hho-lucene-code")
                .version("1.0").contact(new Contact("郑延康", "https://github.com/fandafu98/hho-lucene-code", "")).build();
    }


}
