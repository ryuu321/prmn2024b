package jp.ac.chitose.ir.presentation.views.usermanagement;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import jp.ac.chitose.ir.presentation.component.MainLayout;

@PageTitle("UserDelete")
@Route(value = "/user_management/delete", layout = MainLayout.class)
@RolesAllowed({"administrator"})
public class UserDeleteView extends VerticalLayout {
    private TextField userIDTextField;
    private TextField userNameTextField;
    private Button deleteAccount;
    private Button cancelButton;

    public UserDeleteView() {

        initializeTextField();
        initializeButton();
        addComponents();
    };

    // テキストフィールドの初期設定
    private void initializeTextField() {
        userIDTextField = new TextField("ユーザーID");
        userNameTextField = new TextField("ユーザーネーム");
        FormLayout formLayout = new FormLayout(userIDTextField, userNameTextField);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
    }

    // ボタンの初期設定
    private void initializeButton() {
        deleteAccount = new Button("削除", new Icon(VaadinIcon.MINUS));
        deleteAccount.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelButton = new Button("戻る");
    }

    // 各種コンポーネントの追加
    private void addComponents() {
        add(new H1("ユーザーの削除"), new Paragraph("ユーザーを削除することができます。削除したいユーザーのユーザネームとユーザ名を入力してください。"));
        FormLayout formLayout = new FormLayout(userIDTextField, userNameTextField);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        add(formLayout);
        HorizontalLayout buttonLayout = new HorizontalLayout(deleteAccount, cancelButton);
        buttonLayout.getStyle().set("flex-wrap", "wrap");
        add(buttonLayout);
    }

}
