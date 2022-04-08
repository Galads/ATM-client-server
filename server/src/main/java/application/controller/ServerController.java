package application.controller;

import application.exception.ClientNotFoundException;
import application.model.AuthenticationService;
import application.model.ClientBuilder;
import dto.AuthenticationResponse;
import dto.ClientRequest;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import view.ClientBalance;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/server")
public class ServerController {
    private ClientBuilder clientBuilder;
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
            return Optional.of(clientBuilder.getClientBalance(accountId, pin));
        } catch (ClientNotFoundException ex) {
            return Optional.empty();
        }
    }

    @PostMapping("/login")
    public Optional<ClientBalance> getBalanceClientLoginPass(@RequestBody ClientRequest body) {
        try {
            return Optional.of(clientBuilder.getClientBalance(body.getLogin(), body.getPassword()));
        } catch (ClientNotFoundException ex) {
            return Optional.empty();
        }
    }

    @GetMapping("/all")
    public List<ClientBalance> getAllClients() {
        return clientBuilder.getAllClientBalances();
    }

    @GetMapping("/ping")
    public String helloPath() {
        return "Server available";
    }

    @PostMapping("/auth")
    public AuthenticationResponse authenticateUser(@RequestBody ClientRequest clientRequest) {
        return authenticationService.authenticate(clientRequest);
    }
}
