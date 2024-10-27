package com.soa.workerservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.soa.workerservice.repository")
public class ApplicationConfiguration {
}
