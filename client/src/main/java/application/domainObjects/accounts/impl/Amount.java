package application.domainObjects.accounts.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
class Amount {
    private long cardNumber;
    private String currency;
}
