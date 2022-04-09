package application.model;

import application.entity.Client;
import application.exception.ClientNotFoundException;
import application.repository.ClientRepository;
import application.security.JPAUserDetails;
import dto.RegistrationRequest;
import dto.ServerResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import view.ClientBalance;
import view.Currencies;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientService {

    private ClientRepository clientRepository;

    public List<ClientBalance> getAllClientBalances() { //доступ для всех, сделать для админа
        List<Client> clientBalances = new ArrayList<>();

        clientRepository
                .findAll()
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

    //отрефакторить
    public ClientBalance getClientBalance(long accountId, short pin) {
        JPAUserDetails userDetails = getUserDetails();

        boolean authResult = authenticate(String.valueOf(accountId),
                String.valueOf(pin),
                userDetails::getId,
                userDetails::getPin);
        if (!authResult)
            throw new ClientNotFoundException("Client not found !");

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
        JPAUserDetails userDetails = getUserDetails();

        if (!authenticate(login, pass, userDetails::getUsername, userDetails::getPassword))
            throw new ClientNotFoundException("Client not found !");

        List<Currencies> currencies = new ArrayList<>();

        Client client = clientRepository
                .findByLoginAndPassword(login, pass)
                .orElseThrow(() ->
                        new ClientNotFoundException("Client not found !"));
        fillCurrencies(client, currencies);

        return new ClientBalance(client.getId(), currencies);
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

    private <F, S> boolean authenticate(String first,
                                        String second,
                                        Supplier<F> funcFirst,
                                        Supplier<S> funcSecond) {

        return first.equals(funcFirst.get() + "") && second.equals(funcSecond.get() + "");
    }

    private JPAUserDetails getUserDetails() {
        return (JPAUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
