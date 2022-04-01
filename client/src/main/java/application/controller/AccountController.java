package application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import view.ClientBalance;
import view.ClientError;

import java.util.Optional;

@RestController
@Slf4j
public class AccountController {

    private RestTemplate restTemplate = new RestTemplate();
    @Value("${server.url}")
    private String URL;

    @GetMapping("/ping")
    public String pingClient() {
        return "Hello spring";
    }

    /**
     * 1. Авторизация по пин-коду
     * 2. получение данных клиента из запроса
     * 3. создание сущности клиента
     * 4. отправка по Rest dto клиента
     * 5. получение и возвращение ответа
     */

    @GetMapping("/ATM/accounts/{accountId}/{pin}/pin")
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
     * postForObject
     */
/*    @GetMapping("/ATM/accounts/{accountId}/{login}/{pass}/login")
    public Client getBalanceLoginPass(
            @PathVariable("accountId") long accountId,
            @PathVariable("login") String login,
            @PathVariable("pass") long pass) {

    }*/
}