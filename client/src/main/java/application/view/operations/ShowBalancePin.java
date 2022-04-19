package application.view.operations;

import application.controller.AccountController;
import com.vaadin.flow.router.Route;
import view.ClientBalance;

@Route("home/balance/pin")
public class ShowBalancePin extends Operations {
    public ShowBalancePin(AccountController accountController) {
        super(accountController, "Номер карты (ID):", "PIN код:");
    }

    @Override
    public ClientBalance clientRequest() {
        return accountController.getBalancePin(
                Long.parseLong(settings.getFirstField().getValue()),
                Long.parseLong(settings.getSecondPrivateField().getValue()));
    }
}
