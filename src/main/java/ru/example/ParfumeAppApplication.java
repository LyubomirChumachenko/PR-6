package ru.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("ru.example")  // укажите пакет, где находятся ваши сущности
public class ParfumeAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(ParfumeAppApplication.class, args);
    }
}
