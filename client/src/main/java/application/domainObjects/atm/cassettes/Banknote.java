package application.domainObjects.atm.cassettes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Banknote {
    private String denomination;
    private int currency;
}
