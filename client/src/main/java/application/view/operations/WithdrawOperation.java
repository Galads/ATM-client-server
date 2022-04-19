package application.view.operations;

import application.controller.AccountController;
import com.vaadin.flow.router.Route;
import dto.ClientRequestOperations;
import view.ClientBalance;

import java.math.BigDecimal;

@Route("home/withdraw")
public class WithdrawOperation extends CurrencyOperations {
    public WithdrawOperation(AccountController accountController) {
        super(accountController, "Снять сумму:");
    }

    @Override
    public ClientBalance clientRequest() {
        return accountController.withdraw(new ClientRequestOperations(
                settings.getFirstField().getValue(),
                settings.getSecondPrivateField().getValue(),
                nameCurrency.getValue(),
                new BigDecimal(value.getValue())));
    }
}
