package com.soa.workerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import com.ecwid.consul.v1.ConsulClient;

import javax.ws.rs.core.Application;

@SpringBootApplication(scanBasePackages = "com.soa.workerservice.controller")
@EnableDiscoveryClient

public class WorkerServiceApplication extends SpringBootServletInitializer {

    @Bean
    public ConsulClient consulClient() {
        return new ConsulClient("localhost", 8500);
    }

    @Override
    public SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WorkerServiceApplication.class);
    }
    public static void main(String[] args) {
        System.out.println("Starting Spring Boot application...");
        SpringApplication.run(WorkerServiceApplication.class, args);
        System.out.println("Spring Boot application started.");

    }
} 