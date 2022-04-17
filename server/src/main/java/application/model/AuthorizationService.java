package application.model;

import application.entity.Client;
import application.exception.ClientNotFoundException;
import application.repository.ClientRepository;
import application.security.JPAUserDetails;
import dto.ClientRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class AuthorizationService {
    private ClientRepository clientRepository;

    public Client authorization(ClientRequest request) {
        JPAUserDetails userDetails = getUserDetails();
        boolean authResult = isAuthenticate(request.getLogin(), request.getPassword(),
                userDetails::getUsername, userDetails::getPassword);

        if (!authResult)
            throw new ClientNotFoundException("Client not found !");
        return clientRepository
                .findByLoginAndPassword(request.getLogin(), request.getPassword())
                .orElseThrow(() -> new ClientNotFoundException("Client not found !"));
    }

    public JPAUserDetails getUserDetails() {
        return (JPAUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public <F, S> boolean isAuthenticate(String first,
                                         String second,
                                         Supplier<F> funcFirst,
                                         Supplier<S> funcSecond) {

        return first.equals(funcFirst.get() + "") && second.equals(funcSecond.get() + "");
    }
}
