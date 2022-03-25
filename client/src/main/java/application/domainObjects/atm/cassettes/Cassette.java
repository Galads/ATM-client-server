package application.domainObjects.atm.cassettes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Cassette<T extends Banknote> {
    private List<T> banknotes;
}
