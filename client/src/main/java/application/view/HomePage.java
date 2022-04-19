package application.view;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("home")
public class HomePage extends HorizontalLayout {

    public HomePage() {
        H1 header = new H1("Операции");
        header.setWidth(null);

        VerticalLayout items = new VerticalLayout(
                header,
                createCard("отобразить баланс", VaadinIcon.WALLET, e -> balanceOperation("home/balance")),
                createCard("Отобразить баланс по номеру карты и PIN коду", VaadinIcon.CREDIT_CARD, e -> balanceOperation("home/balance/pin")),
                createCard("внести наличные", VaadinIcon.MONEY_DEPOSIT, e -> balanceOperation("home/deposit")),
                createCard("снять наличные", VaadinIcon.MONEY_WITHDRAW, e -> balanceOperation("home/withdraw")));

        items.setSizeFull();
        items.addClassName("workspace");
        items.setHorizontalComponentAlignment(Alignment.CENTER, header);
        add(items);
    }

    private Component createCard(String name,
                                 VaadinIcon icon,
                                 ComponentEventListener<ClickEvent<Button>> listener) {
        Button button = new Button(name);
        button.addClickListener(listener);
        button.setSizeFull();
        button.setIcon(icon.create());

        FlexLayout card = new FlexLayout(button);

        card.addClassName("card");
        card.setAlignItems(Alignment.CENTER);
        card.setJustifyContentMode(JustifyContentMode.CENTER);
        card.setWidth("100%");
        card.setHeight("200px");

        return card;
    }

    private void balanceOperation(String redirectPath) {
        UI.getCurrent().navigate(redirectPath);
    }
}
