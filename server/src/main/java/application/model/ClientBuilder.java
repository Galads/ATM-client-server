package application.model;

import application.entity.Client;
import application.exception.ClientNotFoundException;
import application.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import view.ClientBalance;
import view.Currencies;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class ClientBuilder {
    private ClientRepository clientRepository;

    public List<ClientBalance> getAllClientBalances() {
        List<Client> clientBalances = new ArrayList<>();

        StreamSupport.stream(clientRepository.findAll().spliterator(), false)
                .forEach(clientBalances::add);

        return clientBalances.stream()
                .map(e -> {
                            List<Currencies> currencies = e.getBalance().stream()
                                    .map(balance -> new Currencies(balance.getName(), balance.getAmount()))
                                    .collect(Collectors.toList());

                            return new ClientBalance(e.getId(), currencies);
                        }
                ).collect(Collectors.toList());
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

