package application.view.pagesettings;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import view.ClientBalance;
import view.Currencies;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StandardSettings {
    private VerticalLayout verticalLayout = new VerticalLayout();
    private Grid<ClientBalance> clientBalanceGrid = new Grid<>(ClientBalance.class, false);
    private Grid<Currencies> currenciesGrid = new Grid<>(Currencies.class);
    private TextField login = new TextField();
    private PasswordField pass = new PasswordField();
    private Button btn = new Button();
    private TextField searchField = new TextField();
}
