package application.controller;

import application.model.ClientService;
import application.model.utils.ResponseWrapper;
import dto.ClientRequest;
import dto.ClientRequestOperations;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import view.ClientBalance;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/server")
public class ServerController {
    private ClientService clientService;

    @GetMapping("/{accountId}/{pin}/balance")
    public Optional<ClientBalance> getBalanceClientIdPin(
            @PathVariable("accountId") long accountId,
            @PathVariable("pin") short pin) {
        return ResponseWrapper.wrap((i, p) -> clientService.getClientBalance(i, p), accountId, pin);
    }

    @PostMapping("/login")
    public Optional<ClientBalance> getBalanceClientLoginPass(@RequestBody ClientRequest request) {
        return ResponseWrapper.wrap((l, p) -> clientService.getClientBalance(l, p),
                request.getLogin(), request.getPassword());
    }

    @PostMapping("/deposit")
    public Optional<ClientBalance> deposit(@RequestBody ClientRequestOperations request) {
        return ResponseWrapper.wrap(clientService::depositCurrency, request);
    }

    @PostMapping("/withdraw")
    public Optional<ClientBalance> withdraw(@RequestBody ClientRequestOperations request) {
        return ResponseWrapper.wrap(clientService::withdrawCurrency, request);
    }

    @GetMapping("/ping")
    public String ping() {
        return "Server available";
    }
}