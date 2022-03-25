package application.controller;

import application.domainObjects.accounts.impl.Client;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @RequestMapping("/")
    public String sayHello() {
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
        return "You're id: " + accountId + " pin: " + pin;
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