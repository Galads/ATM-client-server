package application.view;

import application.controller.AccountController;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import dto.ClientRequest;
import view.ClientBalance;
import view.Currencies;

import java.util.ArrayList;

@Route("home")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class HomePage extends HorizontalLayout {
    private VerticalLayout verticalLayout = new VerticalLayout();
    private AccountController accountController;
    private Grid<ClientBalance> clientBalance = new Grid<>(ClientBalance.class, false);
    private Grid<Currencies> currencies = new Grid<>(Currencies.class);
    private TextField login = new TextField();
    private PasswordField pass = new PasswordField();
    private Button btn = new Button();
    private TextField searchField = new TextField();

    private ClientBalance balance;

    public HomePage(AccountController accountController) {
        this.accountController = accountController;
        initWebParams();
        initSearchHandlers();
        clientHandler(accountController);
    }

    private void initSearchHandlers() {
        searchField.setWidth("50%");
        searchField.setPlaceholder("Поиск");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);

        searchField.addValueChangeListener(e -> listBalances(e.getValue()));
    }

    private void listBalances(String filterText) {
        if (balance == null || balance.getBalance().isEmpty())
            currencies.setItems();
        else
            currencies.setItems(balance.getBalance()
                    .stream()
                    .filter(e -> e.getName().startsWith(filterText.toUpperCase())));
    }

    private void initWebParams() {
        clientBalance.addColumn(ClientBalance::getClientId).setHeader("Client ID");
        clientBalance.setAllRowsVisible(true);
        currencies.setAllRowsVisible(true);

        login.setLabel("логин");
        pass.setLabel("пароль");

        btn.setText("Отправить");

        setWidth("1000px");
        setHeight("400px");
        verticalLayout.setWidth("100px");

        VerticalLayout searchTable = new VerticalLayout(searchField, currencies);
        VerticalLayout clientTable = new VerticalLayout(clientBalance);
        clientTable.getStyle().set("margin-top", "60px");

        add(clientTable, searchTable, verticalLayout);
        verticalLayout.add(login, pass, btn);
    }

    private void clientHandler(AccountController accountController) {
        btn.addClickListener(event -> {
            ClientBalance balance = accountController
                    .getBalanceLoginPass(new ClientRequest(login.getValue(), pass.getValue()));
            this.balance = balance;

            if (balance.getBalance() == null) {
                balance.setBalance(new ArrayList<>());
                setButtonTheme("error");
            } else
                setButtonTheme("primary");

            clientBalance.setItems(balance);
            currencies.setItems(balance.getBalance());
        });
    }

    private void setButtonTheme(String theme) {
        ThemeList themeList = btn.getElement().getThemeList();
        themeList.clear();
        themeList.add(theme);
    }
}
