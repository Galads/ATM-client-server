package domainObjects.accounts;

import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Set;

public interface ClientService {
    BigDecimal getAmount();
    Long getNumberCard();
    Set<Long> getAllAccountsByClientId(@NonNull long clientId);
}
