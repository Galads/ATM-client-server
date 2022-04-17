package dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ClientRequestOperations extends ClientRequest {
    private String currency;
    private BigDecimal value;

    public ClientRequestOperations(String login, String pass, String currency, BigDecimal value) {
        this.login = login;
        this.password = pass;
        this.currency = currency;
        this.value = value;
    }
}
