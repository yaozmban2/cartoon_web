package cn.yu.cartoon.cartoon_web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.yu.cartoon.cartoon_web.mapper")
public class CartoonWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartoonWebApplication.class, args);
    }

}
