package application.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import view.ClientBalance;
import view.ClientError;
import view.Currencies;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void getBalancePinUnAuthorized() throws Exception {
        String url = String.format("/client/%s/%s/pin", "1", "1234");
        String result = "{\"clientId\":0,\"balance\":null,\"status\":\"Forbidden access !\"}";

        doReturn(new ResponseEntity<ClientBalance>(new ClientError("Forbidden access !"), HttpStatus.FORBIDDEN))
                .when(restTemplate)
                .exchange(anyString(), any(HttpMethod.class), any(), ArgumentMatchers.<Class<ClientBalance>>any());

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(result)));
    }

    @Test
    public void getBalanceLoginPassSuccess() throws Exception {
        String url = "/client/login";
        String body = "{\"login\" : \"nikolay\",\"password\" : \"pass\" \n}";
        List<Currencies> balance = new ArrayList<>(Arrays.asList(new Currencies("USD", new BigDecimal("200.0"))));

        doReturn(new ResponseEntity<>(new ClientBalance(1, balance), HttpStatus.OK))
                .when(restTemplate)
                .exchange(anyString(), any(HttpMethod.class), any(), ArgumentMatchers.<Class<ClientBalance>>any());

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("1")))
                .andExpect(content().string(containsString("200.0")))
                .andExpect(content().string(containsString("balance")));
    }

    @Test
    public void getBalanceNotAuthorized() throws Exception {
        String result = "Client not found !: Uncorrected login or password";
        String url = "/client/login";
        String body = "{\"login\" : \"IDK\",\"password\" : \"IDK\" \n}";
        ClientBalance clientBalance = null;

        doReturn(new ResponseEntity<>(clientBalance, HttpStatus.OK))
                .when(restTemplate)
                .exchange(anyString(), any(HttpMethod.class), any(), ArgumentMatchers.<Class<ClientBalance>>any());

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(result)));
    }

    @Test
    public void getBalanceNotRegisteredClient() throws Exception {
        String url = "/client/login";
        String body = "{\"login\" : \"success\",\"password\" : \"success\" \n}";
        String result = "Forbidden";

        doThrow(HttpClientErrorException.class)
                .when(restTemplate)
                .exchange(anyString(), any(HttpMethod.class), any(), ArgumentMatchers.<Class<ClientBalance>>any());

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(result)));
    }
}