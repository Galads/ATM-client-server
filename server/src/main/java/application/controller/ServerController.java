package application.controller;

import application.entity.TestClient;
import application.model.ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.soap.Addressing;
import java.util.List;

@RestController
//@RequestMapping("/catalog")
public class ServerController {

    private RestTemplate restTemplate;

    @Autowired
    private ClientBuilder clientBuilder;

    /**
     * Обращение к БД, валидация клиента
     * Получение баланса -> результат -> баланс конкретного счета аккаунта клиента
     */
/*    @GetMapping("/server/accounts/{accountId}/{pin}/balance")
    public ClientBalance getBalanceClient(
            @PathVariable("accountId") long accountId,
            @PathVariable("pin") long pin) {
//TODO
//        return new ClientBalance(accountId, Arrays.asList(new Currencies("dollar", new BigDecimal("1.0003"))));

    }*/
    @GetMapping("/server/accounts/{accountId}/{pin}/balance")
    public List<TestClient> testMethod(@PathVariable("accountId") long accountId,
                                       @PathVariable("pin") long pin) {
        return clientBuilder.getAllClients();
    }

    @RequestMapping("/hello")
    public String helloPath() {
        return "Server available";
    }
}
