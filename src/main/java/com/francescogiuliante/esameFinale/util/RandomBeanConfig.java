package com.francescogiuliante.esameFinale.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Random;

@Configuration
public class RandomBeanConfig {

    @Bean
    public Random random() {
        return new Random();
    }
}