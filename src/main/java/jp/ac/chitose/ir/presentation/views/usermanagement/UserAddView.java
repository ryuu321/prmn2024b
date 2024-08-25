package jp.ac.chitose.ir.presentation.views.usermanagement;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import jp.ac.chitose.ir.presentation.component.MainLayout;

import java.time.LocalDateTime;

@PageTitle("UserAdd")
@Route(value = "/user_management/add", layout = MainLayout.class)
@RolesAllowed({"administrator"})
public class UserAddView extends VerticalLayout {
    private TextField userIDTextField;
    private TextField userNameTextField;
    private Button deleteAccount;
    private Button cancelButton;

    public UserAddView() {
        initializeTextField();
        initializeButton();
        addComponents();
    }

    // テキストフィールドの初期設定
    private void initializeTextField() {
        userIDTextField = new TextField("ユーザーID");
        userNameTextField = new TextField("ユーザーネーム");
        FormLayout formLayout = new FormLayout(userIDTextField, userNameTextField);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
    }

    // ボタンの初期設定
    private void initializeButton() {
        deleteAccount = new Button("削除", new Icon(VaadinIcon.MINUS), buttonClickEvent -> {
            // ここでDBとやりとりするための情報を取得している(サービスが出来たらデータを引き渡す)
            String userId = userIDTextField.getValue();
            String userName = userNameTextField.getValue();
            LocalDateTime deleteAt = LocalDateTime.now();
        });
        deleteAccount.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelButton = new Button("戻る", buttonClickEvent -> {
            UI.getCurrent().navigate("/user_management");
        });
    }

    // 各種コンポーネントの追加
    private void addComponents() {
        add(cancelButton);
        add(new H1("ユーザーの削除"), new Paragraph("ユーザーを削除することができます。削除したいユーザーのユーザIDとユーザーネームを入力してください。"));
        FormLayout formLayout = new FormLayout(userIDTextField, userNameTextField);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        add(formLayout);
        add(deleteAccount);
    }
}
