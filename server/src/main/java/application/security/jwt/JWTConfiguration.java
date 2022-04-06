package application.security.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTConfiguration {
    @Bean
    public JWT getJwtToken() {
        return new JWT();
    }
}

