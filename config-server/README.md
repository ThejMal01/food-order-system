# Spring Cloud Config Server - Git Repository Integration

## Overview

This guide demonstrates how to configure Spring Cloud Config Server to connect with a Git repository for centralized
configuration management using `application.properties`.

## Prerequisites

- Java 21 or higher
- Maven
- Git repository containing configuration files
- Spring Boot 3.5.0 or higher

## Dependencies

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

## application.properties

Add the following properties to your `application.properties` file in the Config Server project:

```properties
server.port=8888
spring.cloud.config.server.git.uri=<your-git-repo-uri>
spring.cloud.config.server.git.clone-on-start=true
spring.cloud.config.server.git.username=<git-username>
spring.cloud.config.server.git.password=<git-personal-access-token>
```

## Main Application Class

Enable Config Server in your main application class:

```java
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
```

## Configuration

### Basic Git Repository Configuration

Create `application.properties` in your Config Server's `src/main/resources`:

```properties
# Server Configuration
server.port=8888
spring.application.name=config-server

# Git Repository Configuration
spring.cloud.config.server.git.uri=https://github.com/your-username/config-repo.git
spring.cloud.config.server.git.default-label=main

# Optional: Specify search paths within the repository
spring.cloud.config.server.git.search-paths=configs,{application}
```

### Private Repository with Authentication

For private repositories, add authentication:

```properties
# Using Username/Password
spring.cloud.config.server.git.uri=https://github.com/your-username/private-config-repo.git
spring.cloud.config.server.git.username=your-git-username 
spring.cloud.config.server.git.password=your-personal-access-token

# Using SSH (alternative)
# Ensure SSH keys are set up on your default location (~/.ssh/) and GitHub account 
spring.cloud.config.server.git.uri=git@github.com:your-username/private-config-repo.git
spring.cloud.config.server.git.ignore-local-ssh-settings=false
```

### Advanced Configuration Options

```properties
# Clone repository on startup
spring.cloud.config.server.git.clone-on-start=true

# Local repository path (optional)
spring.cloud.config.server.git.basedir=/tmp/config-repo

# Refresh rate (in seconds)
spring.cloud.config.server.git.refresh-rate=60

# Force pull on every request
spring.cloud.config.server.git.force-pull=true

# Timeout configurations
spring.cloud.config.server.git.timeout=10

# Health check
management.endpoint.health.show-details=always
management.endpoints.web.exposure.include=health,info,refresh
```

## Git Repository Structure

Organize your configuration repository as follows:

```
config-repo/
├── application.properties              # Default properties for all applications
├── development.properties          # Development environment
├── production.properties         # Production environment
├── user-service.properties            # Service-specific properties
├── user-service-dev.properties        # Service + environment specific
├── order-service/
│   ├── application.properties
│   └── application-prod.properties
└── shared/
    └── database.properties
```

## Configuration File Naming Convention

Spring Cloud Config follows this naming pattern:

- `{application}-{profile}.properties`
- `{application}.properties`
- `application-{profile}.properties`
- `application.properties`

Examples:

- `user-service-dev.properties` (user-service application, dev profile)
- `application-prod.properties` (all applications, prod profile)

## Testing the Configuration

### Start the Config Server

```bash
mvn spring-boot:run
# or
./gradlew bootRun
```

### Access Configuration via REST API

```bash
curl http://localhost:8888/{profile}/{branch}

# Get configuration for 'user-service' application with 'dev' profile
curl http://localhost:8888/user-service/dev

# Get configuration for 'user-service' application with default profile
curl http://localhost:8888/user-service/default

# Get configuration for specific label/branch
curl http://localhost:8888/user-service/dev/feature-branch

# Get raw properties file
curl http://localhost:8888/{application}-{profile}.properties
```

## Client Application Configuration

Configure client applications to use the Config Server:

```properties
# Client application's bootstrap.properties
spring.application.name=user-service
spring.profiles.active=dev
spring.cloud.config.uri=http://localhost:8888
spring.cloud.config.fail-fast=true
spring.cloud.config.retry.max-attempts=3
```

## Security Configuration (Optional)

### Basic Authentication

```properties
# Config Server
spring.security.user.name=config-user
spring.security.user.password=config-password
spring.security.user.roles=USER

# Client
spring.cloud.config.username=config-user
spring.cloud.config.password=config-password
```

## Health Check Endpoints

Monitor your Config Server:

```bash
# Health check
curl http://localhost:8888/actuator/health

# Environment details
curl http://localhost:8888/actuator/env
```

## Common Issues and Solutions

### Issue 1: Repository Not Found

```
Error: Cannot clone or checkout repository
```

**Solution**: Verify repository URL and authentication credentials.

### Issue 2: SSH Key Issues

```
Error: Auth fail
```

**Solution**: Ensure SSH keys are properly configured or use HTTPS with tokens.

### Issue 3: Configuration Not Refreshing

**Solution**: Enable `spring.cloud.config.server.git.force-pull=true` or implement webhook notifications.

## Best Practices

1. **Use Personal Access Tokens** instead of passwords for Git authentication
2. **Enable clone-on-start** for production environments
3. **Implement proper security** for Config Server endpoints
4. **Use environment-specific branches** or directories
5. **Monitor Config Server health** and performance
6. **Implement configuration encryption** for sensitive data
7. **Use refresh endpoints** or webhooks for dynamic configuration updates

## Environment Variables Override

You can override properties using environment variables:

```bash
export SPRING_CLOUD_CONFIG_SERVER_GIT_URI=https://github.com/new-repo/config.git
export SPRING_CLOUD_CONFIG_SERVER_GIT_USERNAME=new-username
export SPRING_CLOUD_CONFIG_SERVER_GIT_PASSWORD=new-token
```

## Docker Configuration

```dockerfile
FROM openjdk:11-jre-slim
COPY target/config-server.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

```bash
docker run -p 8888:8888 \
  -e SPRING_CLOUD_CONFIG_SERVER_GIT_URI=https://github.com/your-repo/config.git \
  your-config-server:latest
```

## Conclusion

This setup provides a robust, centralized configuration management solution using Spring Cloud Config Server with Git
backend. The configuration supports multiple environments, applications, and deployment scenarios while maintaining
security and flexibility.