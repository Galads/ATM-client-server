package application.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AtmProperties {
    @Value("${server.url}")
    private String SERVER_URL;

    @Value("${server.auth}")
    private String SERVER_AUTH_URL;

    @Value("${server.registration}")
    private String SERVER_REGISTRATION_URL;
}
