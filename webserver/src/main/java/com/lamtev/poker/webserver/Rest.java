package com.lamtev.poker.webserver;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
//@EnableWebMvc
public class Rest {

    public static void main(String[] args) {
        SpringApplication.run(Rest.class, args);
    }

    @Bean
    public Map<String, Room> rooms() {
        return new LinkedHashMap<>();
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }

}

