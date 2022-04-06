package application.configuration;

import application.model.UserContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {
    @Bean
    public UserContext getUserContext() {
        return new UserContext();
    }
}
