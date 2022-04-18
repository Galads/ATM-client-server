package application.view.operations;

import application.controller.AccountController;
import com.vaadin.flow.router.Route;
import dto.ClientRequest;
import view.ClientBalance;

@Route("home/balance")
public class ShowBalance extends Operations {
    public ShowBalance(AccountController accountController) {
        super(accountController);
    }

    @Override
    public ClientBalance clientRequest() {
        return accountController
                .getBalanceLoginPass(new ClientRequest(settings.getLogin().getValue(), settings.getPass().getValue()));
    }
}
