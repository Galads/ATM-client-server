package application.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Route("home2")
@Theme(value = Lumo.class, variant = Lumo.LIGHT)
public class TestHomePage extends HorizontalLayout {

    public TestHomePage() {
        H1 header = new H1("Операции");
        header.setWidth(null);

        VerticalLayout items = new VerticalLayout(
                header,
                createCard("отобразить баланс", VaadinIcon.WALLET),
                createCard("внести наличные", VaadinIcon.MONEY_DEPOSIT),
                createCard("снять наличные", VaadinIcon.MONEY_WITHDRAW));

        items.setSizeFull();
        items.addClassName("workspace");
        items.setHorizontalComponentAlignment(Alignment.CENTER, header);
        add(items);
    }

    private Component createCard(String name, VaadinIcon icon) {
//        Span cardLabel = new Span("card: " + name);
        Button button = new Button(name);
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
}
