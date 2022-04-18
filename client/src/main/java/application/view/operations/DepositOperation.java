package application.view.operations;

import application.controller.AccountController;
import com.vaadin.flow.router.Route;
import dto.ClientRequestOperations;
import view.ClientBalance;

import java.math.BigDecimal;

@Route("home/deposit")
public class DepositOperation extends CurrencyOperations {
    public DepositOperation(AccountController accountController) {
        super(accountController, "Пополнить на:");
    }

    @Override
    public ClientBalance clientRequest() {
        return accountController.deposit(new ClientRequestOperations(
                settings.getLogin().getValue(),
                settings.getPass().getValue(),
                nameCurrency.getValue(),
                new BigDecimal(value.getValue())));
    }
}
