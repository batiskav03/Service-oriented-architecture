package org.soa.workerwebebedded;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.SystemMetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = { SystemMetricsAutoConfiguration.class })
@EnableDiscoveryClient
public class WorkerWebEbeddedApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkerWebEbeddedApplication.class, args);
    }

}
