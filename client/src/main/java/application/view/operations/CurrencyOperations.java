package application.view.operations;

import application.controller.AccountController;
import com.vaadin.flow.component.textfield.TextField;

public abstract class CurrencyOperations extends Operations {
    protected TextField nameCurrency;
    protected TextField value;

    public CurrencyOperations(AccountController accountController, String label) {
        super(accountController);
        nameCurrency = new TextField();
        value = new TextField();

        nameCurrency.setLabel("Наименование валюты:");
        value.setLabel(label);
        settings.getVerticalLayout().addComponentAsFirst(nameCurrency);
        settings.getVerticalLayout().addComponentAsFirst(value);
    }
}
