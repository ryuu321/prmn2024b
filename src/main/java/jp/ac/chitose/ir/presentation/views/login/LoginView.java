package jp.ac.chitose.ir.presentation.views.login;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("login")
@Route(value = "login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
    private LoginForm login;
    private LoginI18n i18n = LoginI18n.createDefault();

    public LoginView() {
        addClassName("login-view");
        setSizeFull();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        LoginI18n.Form i18nForm = i18n.getForm();

        i18nForm.setTitle("ログイン");
        i18nForm.setUsername("ログインID");
        i18nForm.setPassword("パスワード");
        i18nForm.setSubmit("ログイン");
        i18nForm.setForgotPassword("パスワードを忘れた方はこちら");
        i18n.setForm(i18nForm);

        LoginI18n.ErrorMessage i18nErrorMessage = i18n.getErrorMessage();

        i18nErrorMessage.setTitle("ログインIDまたはパスワードが不正です");
        i18nErrorMessage.setUsername("ログインIDを入力してください");
        i18nErrorMessage.setPassword("パスワードを入力してください");
        i18nErrorMessage.setMessage("ログインIDとパスワードが正しいことを確認して、再試行してください");

        login = new LoginForm(i18n);
        login.setForgotPasswordButtonVisible(false);
        login.setAction("login");

        add(new H1("IRポータル(仮)"), login);
        add(login);
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
