package application.view.operations;

import application.controller.AccountController;
import application.view.pagesettings.StandardSettings;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.dom.ThemeList;
import view.ClientBalance;

import java.util.ArrayList;

public abstract class Operations extends HorizontalLayout {
    protected AccountController accountController;
    protected StandardSettings settings;
    protected ClientBalance balance;

    public Operations(AccountController accountController, String fieldNameFirst, String fieldNameSecond) {
        this.accountController = accountController;
        this.settings = new StandardSettings();
        initWebParams(fieldNameFirst, fieldNameSecond);
        initSearchHandlers();
        clientHandler();
    }

    private void initSearchHandlers() {
        settings.getSearchField().setWidth("50%");
        settings.getSearchField().setPlaceholder("Поиск");
        settings.getSearchField().setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        settings.getSearchField().setValueChangeMode(ValueChangeMode.EAGER);

        settings.getSearchField().addValueChangeListener(e -> listBalances(e.getValue()));
    }

    private void listBalances(String filterText) {
        if (balance == null || balance.getBalance().isEmpty())
            settings.getCurrenciesGrid().setItems();
        else
            settings.getCurrenciesGrid()
                    .setItems(balance.getBalance()
                            .stream()
                            .filter(e -> e.getName().startsWith(filterText.toUpperCase())));
    }

    private void initWebParams(String first, String second) {
        setFrontSettings(first, second);
        VerticalLayout clientTable = new VerticalLayout(settings.getClientBalanceGrid());
        clientTable.getStyle().set("margin-top", "60px");
        add(
                clientTable,
                new VerticalLayout(settings.getSearchField(), settings.getCurrenciesGrid()),
                settings.getVerticalLayout());
        settings.getVerticalLayout().add(settings.getFirstField(), settings.getSecondPrivateField(), settings.getBtn());
    }

    private void setFrontSettings(String first,String second) {
        settings.getClientBalanceGrid().addColumn(ClientBalance::getClientId).setHeader("Client ID");
        settings.getClientBalanceGrid().setAllRowsVisible(true);
        settings.getClientBalanceGrid().setAllRowsVisible(true);

        settings.getFirstField().setLabel(first);
        settings.getSecondPrivateField().setLabel(second);
        settings.getBtn().setText("Отправить");

        setWidth("1000px");
        setHeight("400px");
        settings.getVerticalLayout().setWidth("100px");
    }

    private void clientHandler() {
        settings.getBtn().addClickListener(event -> {
            ClientBalance clientBalance = clientRequest();
            balance = clientBalance;

            if (clientBalance.getBalance() == null) {
                clientBalance.setBalance(new ArrayList<>());
                setButtonTheme("error");
            } else
                setButtonTheme("primary");

            settings.getClientBalanceGrid().setItems(clientBalance);
            settings.getCurrenciesGrid().setItems(clientBalance.getBalance());
        });
    }

    public abstract ClientBalance clientRequest();

    protected void setButtonTheme(String theme) {
        ThemeList themeList = settings.getBtn().getElement().getThemeList();
        themeList.clear();
        themeList.add(theme);
    }
}
