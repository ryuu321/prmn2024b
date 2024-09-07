package jp.ac.chitose.ir.presentation.views.usermanagement;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
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
import jp.ac.chitose.ir.application.exception.UserManagementException;
import jp.ac.chitose.ir.application.service.management.SecurityService;
import jp.ac.chitose.ir.application.service.management.UsersService;
import jp.ac.chitose.ir.infrastructure.repository.RoleRepository;
import jp.ac.chitose.ir.presentation.component.MainLayout;
import jp.ac.chitose.ir.presentation.component.notification.ErrorNotification;
import jp.ac.chitose.ir.presentation.component.notification.SuccessNotification;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@PageTitle("UserAdd")
@Route(value = "/user_management/add", layout = MainLayout.class)
@RolesAllowed({"administrator"})
public class UserAddView extends VerticalLayout {
    private final UsersService usersService;
    private TextField userIDTextField;
    private TextField userNameTextField;
    private TextField userPasswordTextField;
    private Button createAccount;
    private Button cancelButton;
    CheckboxGroup<String> checkboxGroup;

    public UserAddView(UsersService usersService, SecurityService securityService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        initializeTextField();
        initializeButton();
        addComponents();
        this.usersService = usersService;
    }

    // テキストフィールドの初期設定
    private void initializeTextField() {
        userIDTextField = new TextField("ユーザーID");
        userNameTextField = new TextField("ユーザーネーム");
        userPasswordTextField = new TextField("パスワード");
        checkboxGroup = new CheckboxGroup<>();
        checkboxGroup.setItems("システム管理者", "IR委員会メンバー","教員", "学生");
    }

    // ボタンの初期設定
    private void initializeButton() {
        createAccount = new Button("追加", new Icon(VaadinIcon.PLUS), buttonClickEvent -> {
            // ここでDBとやりとりするための情報を取得している(サービスが出来たらデータを引き渡す)
            String userId = userIDTextField.getValue();
            String username = userNameTextField.getValue();
//            LocalDateTime createAt = LocalDateTime.now();
            String password = userPasswordTextField.getValue();
            Set<String> selectedRoles = checkboxGroup.getSelectedItems();

            try {
                usersService.addUser(userId, username, password, selectedRoles);
                new SuccessNotification(username + "の追加に成功");
            } catch (UserManagementException e){
                if(e.getMessage().isEmpty()) new ErrorNotification("エラーが発生しました");
                else new ErrorNotification(e.getMessage());
            } catch (RuntimeException e){
                e.printStackTrace();
                new ErrorNotification("エラーが発生しました");
            }
        });
        //deleteAccount.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelButton = new Button("戻る", buttonClickEvent -> {
            UI.getCurrent().navigate("/user_management");
        });
    }

    // 各種コンポーネントの追加
    private void addComponents() {
        add(cancelButton);
        add(new H1("ユーザーの追加"), new Paragraph("ユーザーを追加することができます。追加したいユーザーのユーザID,ユーザーネーム,12文字以上のパスワードを入力し、ロールを選択してください。"));
        FormLayout formLayout = new FormLayout(userIDTextField, userNameTextField, userPasswordTextField);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        add(formLayout);
        add(checkboxGroup);
        add(createAccount);
    }
}
