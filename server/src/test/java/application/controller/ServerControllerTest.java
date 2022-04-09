package application.controller;

import application.entity.Client;
import application.model.AuthenticationService;
import application.repository.ClientRepository;
import application.security.JPAUserDetails;
import application.security.jwt.JWT;
import dto.ClientRequest;
import dto.ServerResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) //окружение которое стартует тесты
@SpringBootTest // запускаем тесты в окружении спрингбут приложения
@AutoConfigureMockMvc // создает структуру классов, которая подменяет слой mvc // фейковое окружение
class ServerControllerTest {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired // mock bean
    private ClientRepository clientRepository;
    @Autowired
    private JWT jwt;

    @Autowired
    ServerController serverController;
    @Autowired
    MockMvc mockMvc;

    @Test
    @Transactional
    void getBalanceClientIdPin() {
    }

    @Test
    void getBalanceClientLoginPass() {
    }

    @Test
    public void forbiddenAccess() throws Exception {
        assertThat(serverController).isNotNull();

        mockMvc.perform(MockMvcRequestBuilders.get("/server/all"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    public void getBalanceByLoginAndPass() {
        String body = "{\n    \"login\" : \"nikolay\",\n    \"password\" : \"pass\" \n}";
        String token = jwt.generateToken(new JPAUserDetails(new Client("nikolay", "password", (short) 1234)));

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
    @SneakyThrows
    public void pingServerAvailable() {
        mockMvc.perform(MockMvcRequestBuilders.get("/server/ping"))
                .andDo(print())
                .andExpect(content().string(containsString("Server available")));
    }

    @Test
    public void registerUser() {

    }
}