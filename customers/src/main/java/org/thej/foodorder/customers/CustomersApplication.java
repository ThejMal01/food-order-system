package org.thej.foodorder.customers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {
        "org.thej.foodorder.master",
        "org.thej.foodorder.customers",
})
@EnableJpaRepositories(basePackages = {
        "org.thej.foodorder.master.repository",
})
@EntityScan(basePackages = "org.thej.foodorder.master.dao")
public class CustomersApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomersApplication.class, args);
    }

}
