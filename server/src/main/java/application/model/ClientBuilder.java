package application.model;

import application.entity.Balance;
import application.entity.Client;
import application.exception.ClientNotFoundException;
import application.repository.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import view.ClientBalance;
import view.Currencies;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ClientBuilder {
    private ClientRepository clientRepository;

    public List<Balance> getAllClientBalances() {
        List<Balance> balances = new ArrayList<>();

        return balances;
    }

    public ClientBalance getClientBalance(long accountId, short pin) {

        List<Currencies> currencies = new ArrayList<>();

        Client client = clientRepository
                .findById(accountId)
                .orElseThrow(() ->
                        new ClientNotFoundException("Client not found !"));

        if (pin != client.getPin())
            throw new ClientNotFoundException("Client not found !");

        fillCurrencies(client, currencies);

        return new ClientBalance(
                client.getId(),
                currencies
        );
    }

    public ClientBalance getClientBalance(String login, String pass) {
        List<Currencies> currencies = new ArrayList<>();

        Client client = clientRepository
                .findByLoginAndPassword(login, pass)
                .orElseThrow(() ->
                        new ClientNotFoundException("Client not found !"));
        fillCurrencies(client, currencies);

        return new ClientBalance(client.getId(), currencies);
    }

    private void fillCurrencies(Client client, List<Currencies> arr) {
        client.getBalance().forEach(e ->
                arr.add(new Currencies(e.getName(), e.getAmount())));
    }
}

