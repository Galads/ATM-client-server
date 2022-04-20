package application.controller;

import application.model.AuthenticationService;
import application.model.ClientService;
import dto.ClientRequest;
import dto.RegistrationRequest;
import dto.ServerResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/server")
public class AuthenticationController {
    private ClientService clientService;
    private AuthenticationService authenticationService;

    @PostMapping("/auth")
    public ServerResponse authenticateUser(@RequestBody ClientRequest request) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/register")
    public ServerResponse registerUser(@RequestBody RegistrationRequest request) {
        return clientService.registration(request);
    }
}
