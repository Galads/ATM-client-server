package application.controller;

import application.model.AuthenticationService;
import application.model.RequestService;
import application.properties.AtmProperties;
import dto.ClientRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import view.ClientBalance;

@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class AccountController {
    private AtmProperties properties;
    private AuthenticationService authenticationService;
    private RequestService requestService;

    @GetMapping("/ping")
    public String pingClient() {
        return "Client service available !";
    }

    /**
     * 1. Авторизация по пин-коду
     * 2. получение данных клиента из запроса
     * 3. создание сущности клиента
     * 4. отправка по Rest dto клиента
     * 5. получение и возврат ответа
     */
    @GetMapping("/{accountId}/{pin}/pin")
    public ClientBalance getBalancePin(
            @PathVariable("accountId") long accountId,
            @PathVariable("pin") long pin) {
        return requestService.requestForObject(
                properties.getURL() + accountId + "/" + pin + "/balance",
                HttpMethod.GET,
                ClientBalance.class
        );
    }

    /**
     * 1. Авторизация по Логину и паролю
     * ...
     */
    @PostMapping("/login")
    public ClientBalance getBalanceLoginPass(@RequestBody ClientRequest body) {
        return requestService.requestForObject(
                properties.getURL() + "/login",
                body,
                HttpMethod.POST,
                ClientBalance.class);
    }

    @PostMapping("/auth")
    public String authenticate(@RequestBody ClientRequest body) {
        return authenticationService.authenticate(body);
    }

    @PostMapping("/logout")
    public String logout() {
        return authenticationService.logout();
    }
}