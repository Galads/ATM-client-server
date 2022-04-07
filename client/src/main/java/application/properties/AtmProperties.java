package application.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AtmProperties {
    @Value("${server.url}")
    private String URL;
}
