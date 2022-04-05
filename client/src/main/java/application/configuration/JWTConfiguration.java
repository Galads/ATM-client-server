package application.configuration;

import application.jwt.JWT;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTConfiguration {
    @Bean
    public JWT getJwtToken() {
        return new JWT();
    }
}
