package application.controller;

import application.model.AuthenticationService;
import application.model.status.Status;
import dto.ClientRequest;
import dto.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class AuthenticationController {
    private AuthenticationService authenticationService;

    @PostMapping("/auth")
    public Status authenticate(@RequestBody ClientRequest request) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/logout")
    public Status logout() {
        return authenticationService.logout();
    }

    @PostMapping("/register")
    public Status register(@RequestBody RegistrationRequest request) {
        return authenticationService.registration(request);
    }
}
