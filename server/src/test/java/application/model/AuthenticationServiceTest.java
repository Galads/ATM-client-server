package application.model;

import dto.ClientRequest;
import dto.ServerResponse;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
class AuthenticationServiceTest {
    @Autowired
    private AuthenticationService authenticationService;

    @Test
    public void authenticateSuccessUser() {
        ClientRequest clientRequest = new ClientRequest("nikolay", "pass");
        ServerResponse response = authenticationService.authenticate(clientRequest);
        assertFalse(response.getResult().isEmpty());
    }

    @Test
    public void authenticateFailUser() {
        ClientRequest clientRequest = new ClientRequest("fail-name", "fail-pass");
        ServerResponse response = authenticationService.authenticate(clientRequest);
        assertTrue(response.getResult().isEmpty());
    }
}