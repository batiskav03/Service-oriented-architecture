package com.soa.workerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import com.ecwid.consul.v1.ConsulClient;

@SpringBootApplication
@EnableDiscoveryClient
public class WorkerServiceApplication {

    @Bean
    public ConsulClient consulClient() {
        return new ConsulClient("localhost", 8500);
    }

    public static void main(String[] args) {
        SpringApplication.run(WorkerServiceApplication.class, args);
    }
} 