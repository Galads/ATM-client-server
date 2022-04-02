package application.controller;

import application.entity.Balance;
import application.exception.ClientNotFoundException;
import application.model.ClientBuilder;
import dto.ClientBody;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import view.ClientBalance;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class ServerController {

    private ClientBuilder clientBuilder;

    /**
     * Обращение к БД, валидация клиента
     * Получение баланса -> результат -> баланс конкретного счета аккаунта клиента
     */
    @GetMapping("/server/accounts/{accountId}/{pin}/balance")
    public Optional<ClientBalance> getBalanceClientIdPin(
            @PathVariable("accountId") long accountId,
            @PathVariable("pin") short pin) {
        try {
            return Optional.of(clientBuilder.getClientBalance(accountId, pin));
        } catch (ClientNotFoundException ex) {
            return Optional.empty();
        }
    }

    @PostMapping("server/accounts/login")
    public Optional<ClientBalance> getBalanceClientLoginPass(@RequestBody ClientBody body) {
        try {
            return Optional.of(clientBuilder.getClientBalance(body.getLogin(), body.getPassword()));
        } catch (ClientNotFoundException ex) {
            return Optional.empty();
        }
    }

    @GetMapping("/server/accounts/all")
    public List<Balance> getAllClients() {
        return clientBuilder.getAllClientBalances();
    }

    @GetMapping("/server/ping")
    public String helloPath() {
        return "Server available";
    }
}
