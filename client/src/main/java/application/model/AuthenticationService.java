package application.model;

import application.model.status.Status;
import application.properties.AtmProperties;
import dto.ClientRequest;
import dto.ServerResponse;
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
        ServerResponse serverResponse = responseUrl(atmProperties.getSERVER_AUTH_URL(), clientRequest);
        if (serverResponse != null && !serverResponse.getResult().isEmpty()) {
            userContext.setJwtToken(serverResponse.getResult());
            return Status.SUCCESS;
        }
        return Status.FAILURE;
    }

    public Status registration(ClientRequest clientRequest) {
        ServerResponse serverResponse = responseUrl(atmProperties.getSERVER_REGISTRATION_URL(), clientRequest);

        if (serverResponse != null && !serverResponse.getResult().isEmpty())
            return Status.SUCCESS;
        return Status.FAILURE;
    }

    private ServerResponse responseUrl(String URL, ClientRequest request) {
        return restTemplate.postForObject(
                URL,
                request,
                ServerResponse.class
        );
    }

    public Status logout() {
        userContext.setJwtToken(null);
        return Status.SUCCESS;
    }
}
