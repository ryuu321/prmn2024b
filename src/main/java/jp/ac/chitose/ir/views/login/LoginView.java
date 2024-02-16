package jp.ac.chitose.ir.views.login;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jp.ac.chitose.ir.views.MainLayout;

@PageTitle("login")
@Route(value = "login", layout = MainLayout.class)
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
    private LoginForm login = new LoginForm();

    public LoginView() {
            addClassName("login-view");
            setSizeFull();

            setJustifyContentMode(JustifyContentMode.CENTER);
            setAlignItems(Alignment.CENTER);

            login.setAction("login");

            add(new H1("Test Application"), login);
        }

        @Override
        public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
            if(beforeEnterEvent.getLocation()
                    .getQueryParameters()
                    .getParameters()
                    .containsKey("error")) {
                login.setError(true);
            }

    }
}
