package application.model;

import application.model.status.Status;
import dto.ClientRequest;
import dto.ServerResponse;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthenticationServiceTest {
    @Autowired
    private AuthenticationService authenticationService;
    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void authenticationAndRegistrationSuccess() {
        doReturn(new ServerResponse("token")).when(restTemplate)
                .postForObject(anyString(), any(), ArgumentMatchers.<Class<ServerResponse>>any());

        Status statusAuth = authenticationService.authenticate(new ClientRequest());
        Status statusReg = authenticationService.registration(new ClientRequest());
        assertEquals(statusAuth + "", "SUCCESS");
        assertEquals(statusAuth, statusReg);
    }

    @Test
    public void authenticationAndRegistrationFailure() {
        doReturn(null).when(restTemplate)
                .postForObject(anyString(), any(), ArgumentMatchers.<Class<ServerResponse>>any());

        Status statusAuth = authenticationService.authenticate(new ClientRequest());
        Status statusReg = authenticationService.registration(new ClientRequest());
        assertEquals(statusAuth + "", "FAILURE");
        assertEquals(statusAuth, statusReg);
    }

}