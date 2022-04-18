package application.model;

import application.entity.Client;
import application.exception.ClientNotFoundException;
import application.repository.ClientRepository;
import application.security.JPAUserDetails;
import dto.ClientRequest;
import dto.ClientRequestOperations;
import dto.RegistrationRequest;
import dto.ServerResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import view.ClientBalance;
import view.Currencies;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientService {
    private ClientRepository clientRepository;
    private AuthorizationService authorizationService;
    private AuthorizationDetails authorizationDetails;

    public ClientBalance getClientBalance(long accountId, short pin) {
        JPAUserDetails userDetails = authorizationService.getUserDetails();

        boolean authResult = authorizationService.isAuthenticate(String.valueOf(accountId),
                String.valueOf(pin),
                userDetails::getId,
                userDetails::getPin);
        if (!authResult)
            throw new ClientNotFoundException("Client not found !");

        Client client = clientRepository
                .findById(accountId)
                .orElseThrow(() ->
                        new ClientNotFoundException("Client not found !"));

        List<Currencies> currencies = new ArrayList<>();

        if (pin != client.getPin())
            throw new ClientNotFoundException("Client not found !");

        fillCurrencies(client, currencies);

        return new ClientBalance(client.getId(), currencies);
    }

    public ClientBalance getClientBalance(String login, String pass) {
        Client client = authorizationService.authorization(new ClientRequest(login, pass));
        List<Currencies> currencies = new ArrayList<>();

        fillCurrencies(client, currencies);

        return new ClientBalance(client.getId(), currencies);
    }

    public ClientBalance depositCurrency(ClientRequestOperations request) {
        AuthorizationDetails details = authorizationDetails.getAuthorizedUser(request);

        BigDecimal newAmount = details.getCurrentBalance().getAmount().add(request.getValue());

        details.getCurrentBalance().setAmount(newAmount);
        clientRepository.save(details.getClient());
        fillCurrencies(details.getClient(), details.getCurrencies());

        return new ClientBalance(details.getClient().getId(), details.getCurrencies());
    }

    public ClientBalance withdrawCurrency(ClientRequestOperations request) {
        AuthorizationDetails details = authorizationDetails.getAuthorizedUser(request);

        if (details.getCurrentBalance().getAmount().compareTo(request.getValue()) >= 0) {
            BigDecimal newAmount = details.getCurrentBalance().getAmount().subtract(request.getValue());
            details.getCurrentBalance().setAmount(newAmount);
        }

        clientRepository.save(details.getClient());
        fillCurrencies(details.getClient(), details.getCurrencies());

        return new ClientBalance(details.getClient().getId(), details.getCurrencies());
    }

    public ServerResponse registration(RegistrationRequest request) {
        clientRepository.save(new Client(
                request.getLogin(),
                request.getPassword(),
                request.getPin()));
        return new ServerResponse("Ok");
    }

    private void fillCurrencies(Client client, List<Currencies> arr) {
        client.getBalance().forEach(e ->
                arr.add(new Currencies(e.getName(), e.getAmount())));
    }
}