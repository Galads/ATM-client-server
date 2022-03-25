package application.domainObjects.accounts.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Client {
    private long id;
    private List<Amount> amounts;
}
