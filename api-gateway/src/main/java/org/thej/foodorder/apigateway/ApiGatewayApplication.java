package org.thej.foodorder.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.security.SecureRandom;
import java.util.Base64;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {
        "org.thej.foodorder.master",
        "org.thej.foodorder.webcommons",
        "org.thej.foodorder.apigateway",})
@EnableJpaRepositories(basePackages = {"org.thej.foodorder.master.repository"})
@EntityScan(basePackages = "org.thej.foodorder.master.dao")
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    public static String generateSecureToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[32]; // 32 bytes = 256 bits
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }

}
