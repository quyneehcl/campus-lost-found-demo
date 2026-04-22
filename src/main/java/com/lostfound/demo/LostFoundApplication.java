package com.lostfound.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.lostfound.demo.services.AuthService;
import com.lostfound.demo.services.ItemSearcher;
import com.lostfound.demo.services.ItemService;
import com.lostfound.demo.services.MatchingService;

/**
 * LostFoundApplication - Main entry point for the Spring Boot application.
 * VinUniversity Campus Lost & Found System
 * COMP1020 Object-Oriented Programming & Data Structures - Spring 2026
 * Team Name: Group 12
 */
@SpringBootApplication
public class LostFoundApplication {

    public static void main(String[] args) {
        SpringApplication.run(LostFoundApplication.class, args);
    }

    @Bean
    public ItemService itemService() {
        return new ItemService();
    }

    @Bean
    public AuthService authService() {
        return new AuthService();
    }

    @Bean
    public MatchingService matchingService() {
        return new MatchingService();
    }

    @Bean
    public ItemSearcher itemSearcher() {
        return new ItemSearcher();
    }
}
