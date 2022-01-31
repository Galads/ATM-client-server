package domainObjects.atm.cassettes;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class Banknote {
    @Setter
    @Getter
    @NonNull
    private int denomination;

    @Getter
    @Setter
    private int currency;

    public enum Currencies {
        RUB,
        USD,
        EUR
    }
}
