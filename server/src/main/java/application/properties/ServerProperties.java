package application.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ServerProperties {
    @Value("${server.auth.secret}")
    private String SECRET_KEY;
}