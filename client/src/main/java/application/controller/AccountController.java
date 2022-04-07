package application.controller;

import application.model.AuthenticationService;
import dto.ClientRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import view.ClientBalance;
import view.ClientError;

import java.util.Optional;

@RestController
@RequestMapping("/client")
public class AccountController {
    @Value("${server.url}")
    private String URL;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/ping")
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
    @GetMapping("/{accountId}/{pin}/pin")
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
    @PostMapping("/login")
    public ClientBalance getBalanceLoginPass(
            @RequestBody ClientRequest body) {
        return Optional
                .ofNullable(restTemplate.postForObject(URL + "/login", body, ClientBalance.class))
                .orElseGet(() -> new ClientError("Client not found !: Uncorrected login or password"));
    }

    @PostMapping("/auth")
    public String authenticate(@RequestBody ClientRequest body) {
        return authenticationService.authenticate(body);
    }
}