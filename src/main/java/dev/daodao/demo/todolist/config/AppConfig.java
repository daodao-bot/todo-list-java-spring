package dev.daodao.demo.todolist.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class AppConfig {

    @Value("${spring.application.name:}")
    private String application;

}
