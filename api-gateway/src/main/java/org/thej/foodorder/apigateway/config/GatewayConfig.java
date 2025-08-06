package org.thej.foodorder.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth", r -> r.path("/auth/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://auth"))
                .route("restaurant", r -> r.path("/restaurant/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://restaurants"))
                .route("customer", r -> r.path("/customer/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://customers"))
                .route("admin", r -> r.path("/admin/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://admin"))
                .build();
    }
}
