package application.model;

import application.model.status.Status;
import application.properties.AtmProperties;
import dto.AuthenticationResponse;
import dto.ClientRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@Getter
public class AuthenticationService {
    private AtmProperties atmProperties;
    private UserContext userContext;
    private RestTemplate restTemplate;

    public Status authenticate(ClientRequest clientRequest) {
        AuthenticationResponse authenticationResponse = restTemplate.postForObject(
                atmProperties.getSERVER_AUTH_URL(),
                clientRequest,
                AuthenticationResponse.class
        );

        if (authenticationResponse != null && !authenticationResponse.getJwt().isEmpty()) {
            userContext.setJwtToken(authenticationResponse.getJwt());
            return Status.SUCCESS;
        }
        return Status.FAILURE;
    }

    public Status logout() {
        userContext.setJwtToken(null);
        return Status.SUCCESS;
    }
}
