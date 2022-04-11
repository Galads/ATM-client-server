package application.controller;

import application.entity.Client;
import application.model.AuthenticationService;
import application.security.JPAUserDetails;
import application.security.jwt.JWT;
import dto.ClientRequest;
import dto.ServerResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
class ServerControllerTest {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private JWT jwt;
    @Autowired
    private MockMvc mockMvc;
    private String token;

    @BeforeAll
    public void initToken() {
        token = jwt.generateToken(new JPAUserDetails(new Client("nikolay", "password", (short) 1234)));
    }

    @Test
    public void getBalanceClientIdPin() throws Exception {
        String url = String.format("/server/%s/%s/balance", "1", "1234");

        mockMvc.perform(MockMvcRequestBuilders
                        .get(url)
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"balance\":")))
                .andExpect(content().string(containsString("\"name\":\"RUB\"")));
    }

    @Test
    public void forbiddenAccess() throws Exception {
        String url = String.format("/server/%s/%s/balance", "1", "1234");

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void getBalanceByLoginAndPass() throws Exception {
        String body = "{\n    \"login\" : \"nikolay\",\n    \"password\" : \"pass\" \n}";

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/server/login")
                        .header("Authorization", "Bearer " + token)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("{\"clientId\":1,\"balance\":")));
    }

    @Test
    public void authenticateUser() {
        ClientRequest request = Mockito.mock(ClientRequest.class);

        when(request.getLogin()).thenReturn("nikolay");
        when(request.getPassword()).thenReturn("pass");

        ServerResponse response = authenticationService.authenticate(request);
        assertFalse(response.getResult().isEmpty());
    }

    @Test
    public void pingServerAvailable() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/server/ping"))
                .andDo(print())
                .andExpect(content().string(containsString("Server available")));
    }

    @Test
    public void registerUser() throws Exception {
        String body = "{\"login\" : \"login\",\"password\" : \"pass\", \"pin\" : 4444 }";
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/server/register")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(containsString("Ok")));
    }
}