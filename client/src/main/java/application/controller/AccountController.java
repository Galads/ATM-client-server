package application.controller;

import dto.ClientBody;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    public ClientBalance getBalanceLoginPass(
            @RequestBody ClientBody body) {
        return Optional
                .ofNullable(restTemplate.postForObject(URL + "/login", body, ClientBalance.class))
                .orElseGet(() -> new ClientError("Client not found ! : Uncorrected login or password"));
    }
}