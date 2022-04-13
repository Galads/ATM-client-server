package application.view;

import application.controller.AccountController;
import application.model.status.Status;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import dto.ClientRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Route("login")
@PageTitle("Login ATM")
@Theme(variant = "valo")
public class Login extends VerticalLayout implements BeforeEnterObserver {
    @Autowired
    AccountController accountController;

    private final LoginForm login = new LoginForm();

    public Login() {

        addClassName("login");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");

        add(new H1("ATM"), login);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Map<String, List<String>> params = beforeEnterEvent
                .getLocation()
                .getQueryParameters()
                .getParameters();

        if (params.containsKey("error")) {
            login.setError(true);
        } else if (params.size() != 0) {
            Status status = accountController.authenticate(new ClientRequest(params.get("username").get(0),
                    params.get("password").get(0)));
            if (status.equals(Status.SUCCESS)) {
                login.setEnabled(true);
                beforeEnterEvent.rerouteTo(HomePage.class);
            }
            login.setError(true);
        }
    }
}