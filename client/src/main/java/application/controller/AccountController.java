package application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import view.ClientBalance;

@RestController
@Slf4j
public class AccountController {

    RestTemplate restTemplate = new RestTemplate();

    @RequestMapping("/ping")
    public String pingClient() {
        return "Hello spring";
    }

    /**
     * 1. Авторизация по пин-коду
     * 2. получение данных клиента из запроса
     * 3. создание сущности клиента
     * 4. отправка по Rest dto клиента
     * 5. получение и возвращение ответа
     */

    @GetMapping("/ATM/accounts/{accountId}/{pin}/pin")
    public String getBalancePin( // return Client
                                 @PathVariable("accountId") long accountId,
                                 @PathVariable("pin") long pin) {

         String res = restTemplate
                .getForObject("http://atm-server:8081/server/accounts/" + accountId + "/" + pin + "/balance", String.class);
        return res;
    }

    /**
     * 1. Авторизация по Логину и паролю
     * ...
     */
/*    @GetMapping("/ATM/accounts/{accountId}/{login}/{pass}/login")
    public Client getBalanceLoginPass(
            @PathVariable("accountId") long accountId,
            @PathVariable("login") String login,
            @PathVariable("pass") long pass) {

    }*/


}