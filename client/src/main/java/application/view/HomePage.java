package application.view;

import application.controller.AccountController;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import dto.ClientRequest;
import view.ClientBalance;
import view.Currencies;

import java.util.ArrayList;

@Route("home")
public class HomePage extends HorizontalLayout {
    private VerticalLayout verticalLayout = new VerticalLayout();

    private AccountController accountController;
    private Grid<ClientBalance> clientBalance = new Grid<>(ClientBalance.class);
    private Grid<Currencies> currencies = new Grid<>(Currencies.class);
    private TextField login = new TextField();
    private TextField pass = new TextField();
    private Button btn = new Button();

    public HomePage(AccountController accountController) {
        this.accountController = accountController;
        login.setLabel("login");
        pass.setLabel("password");
        btn.setText("Отправить");

        verticalLayout.setWidth("100px");
        add(clientBalance, currencies, verticalLayout);
        verticalLayout.add(login, pass, btn);

        btn.addClickListener(event -> {
            ClientBalance balance = accountController
                    .getBalanceLoginPass(new ClientRequest(login.getValue(), pass.getValue()));
            if (balance.getBalance() == null) {
                balance.setBalance(new ArrayList<>());
            }

            clientBalance.setItems(balance);
            currencies.setItems(balance.getBalance());
        });
    }
}
