package application.model;

import dto.ClientRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import view.ClientBalance;
import view.ClientError;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RequestService {
    private AuthenticationService authenticationService;
    private RestTemplate restTemplate;

    public ClientBalance requestForObject(String url, HttpMethod method, Class<ClientBalance> type) {
        try {
            return createRequest(url, method, getEntityWithToken(), type);
        } catch (HttpClientErrorException ex) {
            return new ClientError("Forbidden access !");
        }
    }

    public ClientBalance requestForObject(String url, ClientRequest body, HttpMethod method, Class<ClientBalance> type) {
        try {
            return createRequest(url, method, getEntityWithToken(body), type);
        } catch (HttpClientErrorException ex) {
            return new ClientError("Forbidden access !");
        }
    }

    public ClientBalance createRequest(String url,
                                       HttpMethod method,
                                       HttpEntity<ClientRequest> httpRequest,
                                       Class<ClientBalance> type) {

        ResponseEntity<ClientBalance> response = restTemplate.exchange(url, method, httpRequest, type);
        return Optional
                .ofNullable(response.getBody())
                .orElseGet(() -> new ClientError("Client not found !: Uncorrected login or password"));
    }

    private HttpEntity<ClientRequest> getEntityWithToken() {
        return new HttpEntity<>(createHeader());
    }

    private HttpEntity<ClientRequest> getEntityWithToken(ClientRequest body) {
        return new HttpEntity<>(body, createHeader());
    }

    private HttpHeaders createHeader() {
        HttpHeaders headers = new HttpHeaders();
        if (authenticationService.getUserContext().getJwtToken() != null)
            headers.set("Authorization", "Bearer " + authenticationService.getUserContext().getJwtToken());
        return headers;
    }
}