package application.controller;

import application.entity.Balance;
import application.exception.ClientNotFoundException;
import application.model.ClientBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import view.ClientBalance;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class ServerController {

    private RestTemplate restTemplate;
    private ClientBuilder clientBuilder;

    /**
     * Обращение к БД, валидация клиента
     * Получение баланса -> результат -> баланс конкретного счета аккаунта клиента
     */
    @GetMapping("/server/accounts/{accountId}/{pin}/balance")
    public Optional<ClientBalance> getBalanceClient(
            @PathVariable("accountId") long accountId,
            @PathVariable("pin") short pin) {
        try {
            return Optional.of(clientBuilder.getClientBalance(accountId, pin));
        } catch (ClientNotFoundException ex) {
            return Optional.empty();
        }
    }

    @GetMapping("/server/clients")
    public List<Balance> getAllClients() {
        return clientBuilder.getAllClientBalances();
    }

    @RequestMapping("/server/ping")
    public String helloPath() {
        return "Server available";
    }
}
