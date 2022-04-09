package application.controller;

import application.exception.ClientNotFoundException;
import application.model.AuthenticationService;
import application.model.ClientService;
import dto.RegistrationRequest;
import dto.ServerResponse;
import dto.ClientRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import view.ClientBalance;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/server")
public class ServerController {
    private ClientService clientService;
    private AuthenticationService authenticationService;

    /**
     * Обращение к БД, валидация клиента
     * Получение баланса -> результат -> баланс конкретного счета аккаунта клиента
     */
    @GetMapping("/{accountId}/{pin}/balance") // admin
    public Optional<ClientBalance> getBalanceClientIdPin(
            @PathVariable("accountId") long accountId,
            @PathVariable("pin") short pin) {
        try {
            return Optional.of(clientService.getClientBalance(accountId, pin));
        } catch (ClientNotFoundException ex) {
            return Optional.empty();
        }
    }

    @PostMapping("/login")
    public Optional<ClientBalance> getBalanceClientLoginPass(@RequestBody ClientRequest request) {
        try {
            return Optional.of(clientService.getClientBalance(request.getLogin(), request.getPassword()));
        } catch (ClientNotFoundException ex) {
            return Optional.empty();
        }
    }

    @GetMapping("/all")
    public List<ClientBalance> getAllClients() {
        return clientService.getAllClientBalances();
    }

    @GetMapping("/ping")
    public String ping() {
        return "Server available";
    }

    @PostMapping("/auth")
    public ServerResponse authenticateUser(@RequestBody ClientRequest request) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/register")
    public ServerResponse registerUser(@RequestBody RegistrationRequest request) {
        return clientService.registration(request);
    }
}
