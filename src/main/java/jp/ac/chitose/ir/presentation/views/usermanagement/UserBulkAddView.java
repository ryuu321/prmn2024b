package jp.ac.chitose.ir.presentation.views.usermanagement;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import jp.ac.chitose.ir.presentation.component.MainLayout;
import jp.ac.chitose.ir.presentation.component.UploadButton;

import java.io.InputStream;

@PageTitle("UserBulkAdd")
@Route(value = "/user_management/bulk_add", layout = MainLayout.class)
@RolesAllowed({"administrator"})
public class UserBulkAddView extends VerticalLayout {
    private Button cancelButton;
    private Upload upload;

    public UserBulkAddView() {
        initializeButton();
        initializeUploadButton();
        addComponents();
    }



    // ボタン
    private void initializeButton() {
        cancelButton = new Button("戻る", buttonClickEvent -> {
            UI.getCurrent().navigate("/user_management");
        });
    }

    // ファイルのアップロードボタン
    private void initializeUploadButton() {
        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        upload = new Upload(buffer);
        upload.setAutoUpload(false);

        // csv形式のファイルのみ受付
        upload.setAcceptedFileTypes("text/csv", ".csv");

        Button uploadButton = new Button("追加");
        uploadButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


        UploadButton i18n = new UploadButton();
        upload.setI18n(i18n);

        upload.setUploadButton(uploadButton);

        upload.addSucceededListener(event -> {
            String filename = event.getFileName();
            InputStream inputStream = buffer.getInputStream(filename);
        });
    }

    // 各種コンポーネントの追加
    private void addComponents() {
        add(new H1("ユーザーの一括追加"), new Paragraph("ユーザーを一括で追加できます。追加したいユーザーの情報を入力してください。"));
        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton);
        buttonLayout.getStyle().set("flex-wrap", "wrap");
        add(buttonLayout);
        add(upload);
    }

    // データアップロード用のボタン追加
    private void addUploadButton() {

    }
}
