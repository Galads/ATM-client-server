package domainObjects.accounts.impl;

import domainObjects.accounts.ClientService;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Set;

public class Client implements ClientService {

    @Override
    public BigDecimal getAmount() {
        return null;
    }

    @Override
    public Long getNumberCard() {
        return null;
    }

    @Override
    public Set<Long> getAllAccountsByClientId(@NonNull long clientId) {
        return null;
    }
}
