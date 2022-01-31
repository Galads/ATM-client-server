package domainObjects.atm.cassettes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class Cassettes<T extends Banknote> {
    @Getter
    private List<T> arrBanknotes;
}
