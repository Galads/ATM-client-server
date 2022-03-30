package application.controller;

import application.entity.Balance;
import application.model.ClientBuilder;
import application.view.ClientBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class ServerController {

    private RestTemplate restTemplate;

    @Autowired // вынести в одну или две конфигурации?
    private ClientBuilder clientBuilder;

    /**
     * Обращение к БД, валидация клиента
     * Получение баланса -> результат -> баланс конкретного счета аккаунта клиента
     */
    @GetMapping("/server/accounts/{accountId}/{pin}/balance")
    public ClientBalance getBalanceClient(
            @PathVariable("accountId") long accountId,
            @PathVariable("pin") short pin) {
        return clientBuilder.getClientBalance(accountId, pin);
    }
    @GetMapping("/server/clients")
    public List<Balance> testMethod() {
        return clientBuilder.getAllClientBalances();
    }

    @RequestMapping("/ping")
    public String helloPath() {
        return "Server available";
    }
}
