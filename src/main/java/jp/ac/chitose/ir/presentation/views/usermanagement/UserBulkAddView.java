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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
        upload.setDropAllowed(true);
        upload.setAutoUpload(false);

        // csv形式のファイルのみ受付
        upload.setAcceptedFileTypes("text/csv", ".csv");
        // 1度にアップロードできるファイルを1つに設定
        upload.setMaxFiles(1);

        Button uploadButton = new Button("追加");
        uploadButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


        UploadButton i18n = new UploadButton();
        upload.setI18n(i18n);

        upload.setUploadButton(uploadButton);

        upload.addSucceededListener(event -> {
            String filename = event.getFileName();
            InputStream inputStream = buffer.getInputStream(filename);

//            csvの読み込み
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;

                //todo csvが空の場合のエラー処理（flag==0とかで管理）


                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");

                    if(data.length < 3) {
                        //todo 処理を中断し、画面にエラーメッセ―ジを表示
                    }
                    // csvの情報を取得
                    String login_id = data[0];
                    String userName = data[1];
                    // ロールを可変長配列に格納する
                    boolean ROLEAdministrator = Boolean.parseBoolean(data[2]);
                    boolean ROLECommission = Boolean.parseBoolean(data[3]);
                    boolean ROLETeacher = Boolean.parseBoolean(data[4]);
                    boolean ROLEStudent = Boolean.parseBoolean(data[5]);

                    // 取得した情報をServiceに引き渡すなど

                }
            } catch (IOException e) {
                e.printStackTrace();
                // エラーハンドリングをここで行う
            }
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
}