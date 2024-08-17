package jp.ac.chitose.ir.presentation.views.usermanagement;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import jp.ac.chitose.ir.presentation.component.MainLayout;

import java.time.LocalDateTime;

@PageTitle("UserBulkAdd")
@Route(value = "/user_management/bulk_add", layout = MainLayout.class)
@RolesAllowed({"administrator"})
public class UserBulkAddView extends VerticalLayout {
    private Button addAccount;
    private Button cancelButton;

    public UserBulkAddView() {
        initializeButton();
        addComponents();
    };

    // ボタンの初期設定
    private void initializeButton() {
        addAccount = new Button("削除", new Icon(VaadinIcon.MINUS), buttonClickEvent -> {
            // ここでDBとやりとりするための情報を取得している(サービスが出来たらデータを引き渡す)

            LocalDateTime deleteAt = LocalDateTime.now();
        });
        addAccount.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelButton = new Button("戻る", buttonClickEvent -> {
            UI.getCurrent().navigate("/user_management");
        });
    }
    // 各種コンポーネントの追加
    private void addComponents() {
        add(new H1("ユーザーの削除"), new Paragraph("ユーザーを削除することができます。削除したいユーザーのユーザIDとユーザーネームを入力してください。"));
        HorizontalLayout buttonLayout = new HorizontalLayout(addAccount, cancelButton);
        buttonLayout.getStyle().set("flex-wrap", "wrap");
        add(buttonLayout);
    }
}
