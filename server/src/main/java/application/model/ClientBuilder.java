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

// model of project
@Service
@AllArgsConstructor
@Slf4j
public class ClientBuilder {
    private ClientRepository clientRepository;

    public List<Balance> getAllClientBalances() {
        List<Balance> balances = new ArrayList<>();
        /*clientRepository
                .findAll()
                .forEach(balances::add);*/

        return balances;
    }

    public ClientBalance getClientBalance(long accountId, short pin) {

/*        Client balance = clientRepository
                .findById(accountId) // как найти по client_id
                .get();*/

        List<Currencies> currencies = new ArrayList<>();

        Client client = clientRepository
                .findById(accountId)
                .orElseThrow(() ->
                new ClientNotFoundException("Client not found !"));

        if (pin != client.getPin())
            throw  new ClientNotFoundException("Client not found !");
        client.getBalance().forEach(e ->
                currencies.add(new Currencies(e.getName(), e.getAmount()))
        );

        return new ClientBalance(
                client.getId(),
                currencies
        );
    }
}

