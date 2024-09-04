package jp.ac.chitose.ir.presentation.views.settings;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jp.ac.chitose.ir.application.service.management.SecurityService;
import jp.ac.chitose.ir.application.service.management.UsersService;
import jp.ac.chitose.ir.presentation.component.MainLayout;
import jp.ac.chitose.ir.presentation.component.notification.ErrorNotification;
import jp.ac.chitose.ir.presentation.component.notification.SuccessNotification;

@PageTitle("PasswordUpdate")
@Route(value = "/settings/password", layout = MainLayout.class)
@PermitAll
public class PasswordUpdateView extends VerticalLayout {
    private final UsersService usersService;
    private final SecurityService securityService;
    private PasswordField prePasswordTextField;
    private PasswordField newPasswordTextField;
    private PasswordField confirmPasswordTextField;
    private Button updatePassword;
    private Button cancelButton;

    // コンストラクタ
    public PasswordUpdateView(UsersService usersService, SecurityService securityService) {
        this.usersService = usersService;
        this.securityService = securityService;
        initializeTextField();
        initializeButton();
        addComponents();
    }

    private void initializeTextField(){
        prePasswordTextField = new PasswordField("現在のパスワード");
        newPasswordTextField = new PasswordField("新しいパスワード");
        confirmPasswordTextField = new PasswordField("新しいパスワード(確認用)");
    }

    private void initializeButton(){
        updatePassword = new Button("適用", new Icon(VaadinIcon.PLAY), buttonClickEvent -> {
            String prePassword = prePasswordTextField.getValue();
            String newPassword = newPasswordTextField.getValue();
            String confirmPassword = confirmPasswordTextField.getValue();
            int result = usersService.updateLoginUserPassword(prePassword, newPassword, confirmPassword);
            switch (result){
                case 0:
                    // 正常終了の場合
                    new SuccessNotification("パスワードの変更が完了しました");
                    securityService.logout();
                    break;
                case 1:
                    // 入力が空の場所が存在している場合
                    // todo 正規表現を使う場合消えるかも
                    new ErrorNotification("入力が空のフィールドが存在します");
                    break;
                case 2:
                    // 現在のパスワードが正しくない場合
                    new ErrorNotification("現在のパスワードが正しくありません");
                    break;
                case 3:
                    // 新しいパスワードの入力が異なっている場合
                    new ErrorNotification("新しいパスワードが一致していません");
                    break;
                case 4:
                    // 新しいパスワードが現在のパスワードと一致している場合
                    new ErrorNotification("現在のパスワードと新しいパスワードが一致しています");
                    break;
                default:
                    new ErrorNotification("予期せぬエラーが発生しました");
            }
        });
        updatePassword.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        cancelButton = new Button("戻る", buttonClickEvent -> {
            UI.getCurrent().navigate("/top");
        });
    }

    private void addComponents(){
        add(cancelButton);
        add(new H1("パスワード変更"), new Paragraph("パスワードを変更することができます。現在のパスワードと変更後のパスワードを入力してください。※完了後はログイン画面に移行します。"));
        FormLayout formLayout1 = new FormLayout(prePasswordTextField);
        FormLayout formLayout2 = new FormLayout(newPasswordTextField, confirmPasswordTextField);
        formLayout1.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        formLayout2.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        add(formLayout1, formLayout2);
        add(updatePassword);
    }

}
