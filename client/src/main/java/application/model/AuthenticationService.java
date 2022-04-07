package application.model;

import dto.AuthenticationResponse;
import dto.ClientRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final String SERVER_URL_AUTH = "http://atm-server:8081/server/auth";
    private UserContext userContext;
    private RestTemplate restTemplate;

    public String authenticate(ClientRequest clientRequest) {
        AuthenticationResponse authenticationResponse = restTemplate.postForObject(
                SERVER_URL_AUTH,
                clientRequest,
                AuthenticationResponse.class
        );

        if (authenticationResponse != null && !authenticationResponse.getJwt().isEmpty()) {
            userContext.setJwtToken(authenticationResponse.getJwt());
            return "Success";
        }
        return "Failure";
    }
}
