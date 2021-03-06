package application.controller;

import application.model.RequestService;
import application.properties.AtmProperties;
import dto.ClientRequest;
import dto.ClientRequestOperations;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import view.ClientBalance;

@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class AccountController {
    private AtmProperties properties;
    private RequestService requestService;

    @GetMapping("/ping")
    public String pingClient() {
        System.out.println();
        return "Client service available !";
    }

    @GetMapping("/{accountId}/{pin}/pin")
    public ClientBalance getBalancePin(
            @PathVariable("accountId") long accountId,
            @PathVariable("pin") long pin) {
        return requestService.requestForObject(
                properties.getSERVER_URL() + accountId + "/" + pin + "/balance",
                HttpMethod.GET,
                ClientBalance.class
        );
    }

    @PostMapping("/login")
    public ClientBalance getBalanceLoginPass(@RequestBody ClientRequest request) {
        return requestService.requestForObject(
                properties.getSERVER_URL() + "/login",
                request,
                HttpMethod.POST,
                ClientBalance.class);
    }

    @PostMapping("/deposit")
    public ClientBalance deposit(@RequestBody ClientRequestOperations request) {
        return requestService.requestForObject(
                properties.getSERVER_URL() + "/deposit",
                request,
                HttpMethod.POST,
                ClientBalance.class
        );
    }

    @PostMapping("/withdraw")
    public ClientBalance withdraw(@RequestBody ClientRequestOperations request) {
        return requestService.requestForObject(
                properties.getSERVER_URL() + "/withdraw",
                request,
                HttpMethod.POST,
                ClientBalance.class
        );
    }
}