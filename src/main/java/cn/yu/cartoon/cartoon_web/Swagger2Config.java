package cn.yu.cartoon.cartoon_web;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2的配置类
 *
 * @author Yu
 * @version 1.0
 * @date 2019/3/13 16:05
 **/
@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"cn.yu.cartoon.cartoon_web.controller"})
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 文档信息对象
                .apiInfo(apiInfo())
                .select()
                // 被注解的包路径
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 标题
                .title("springboot 利用 swagger 构建 API 文档")
                // Api 文档描述
                .description("漫画分销系统的api")
                .termsOfServiceUrl("https://blog.csdn.net/turodog/")
                // 文档作者信息
                .contact(new Contact("俞竞雄", "", ""))
                // 文档版本
                .version("1.0")
                .build();
    }
}
