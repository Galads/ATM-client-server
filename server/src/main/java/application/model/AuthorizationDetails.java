package application.model;

import application.entity.Balance;
import application.entity.Client;
import dto.ClientRequestOperations;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import view.Currencies;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Getter
public class AuthorizationDetails {
    private AuthorizationService authorizationService;
    private Client client;
    private List<Currencies> currencies;
    private Balance currentBalance;


    public AuthorizationDetails(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    public AuthorizationDetails getAuthorizedUser(ClientRequestOperations request) {
        client = authorizationService.authorization(request);
        currencies = new ArrayList<>();
        Set<Balance> balances = client.getBalance();
        currentBalance = balances.stream()
                .filter(b -> b.getName().equals(request.getCurrency())).findAny().get();
        return this;
    }
}
