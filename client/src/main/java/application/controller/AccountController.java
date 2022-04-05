package application.controller;

import application.configuration.security.service.UserDetailsConfigurationService;
import application.dto.ClientAuthenticationResponseJWT;
import application.jwt.JWT;
import dto.ClientBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import view.ClientBalance;
import view.ClientError;

import java.util.Optional;

@RestController
@Slf4j
public class AccountController {

    @Value("${server.url}")
    private String URL;
    @Autowired
    private RestTemplate restTemplate;

    //test
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsConfigurationService userDetailsConfigurationService;

    @Autowired
    JWT jwt;
    //test

    @GetMapping("/client/ping")
    public String pingClient() {
        return "Client service available !";
    }

    /**
     * 1. Авторизация по пин-коду
     * 2. получение данных клиента из запроса
     * 3. создание сущности клиента
     * 4. отправка по Rest dto клиента
     * 5. получение и возвращение ответа
     */
    @GetMapping("/client/accounts/{accountId}/{pin}/pin")
    public ClientBalance getBalancePin(
            @PathVariable("accountId") long accountId,
            @PathVariable("pin") long pin) {
        return Optional
                .ofNullable(restTemplate.getForObject(URL + accountId + "/" + pin + "/balance", ClientBalance.class))
                .orElseGet(() -> new ClientError("Client not found !"));
    }

    /**
     * 1. Авторизация по Логину и паролю
     * ...
     */
    @PostMapping("/client/accounts/login")
    public ClientBalance getBalanceLoginPass(@RequestBody ClientBody body) {
        return Optional
                .ofNullable(restTemplate.postForObject(URL + "/login", body, ClientBalance.class))
                .orElseGet(() -> new ClientError("Client not found ! : Uncorrected login or password"));
    }

    @PostMapping("/client/auth")
    public ClientAuthenticationResponseJWT getJWTTest(@RequestBody ClientBody body) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(body.getLogin(), body.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Exception: Uncorrected username or password");
        }
        UserDetails user = userDetailsConfigurationService.loadUserByUsername(body.getLogin());
        String jwtToken = jwt.generateToken(user);

        return new ClientAuthenticationResponseJWT(jwtToken);
    }
}