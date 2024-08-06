package jp.ac.chitose.ir.presentation.views.top;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import jakarta.annotation.security.PermitAll;
import jp.ac.chitose.ir.presentation.component.MainLayout;

// Top画面
@PageTitle("IRTop")
@Route(value = "/top", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class TopView extends VerticalLayout {

    public TopView(){
        // タイトル表示　（最も簡単なコンポーネントの使用例）
        H1 title = new H1("CIST IR-Web");
        add(title);

        // 共通メニュー
        H2 menutitle1 = new H2("共通メニュー");
        add(menutitle1);

        // 成績情報ページ（StudentView）の紹介
        Anchor grade = new Anchor("/grade/student", "授業に関する情報公開");
        grade.getElement().setAttribute("target", "");
        Paragraph grade_paragraph =  new Paragraph("成績評価分布や授業評価アンケートの結果を科目ごとに確認できます。");
        grade_paragraph.getStyle().set("top-margin", "0px");
        add(grade, grade_paragraph);

        // 成績評価分布状況表
        Anchor gradegird = new Anchor("", "成績評価分布状況表");
        add(gradegird, new Paragraph("成績評価分布をまとめた表を確認できます。"));

        // 固有メニュー
        H2 menutitle2 = new H2("固有メニュー");
        add(menutitle2);

        // 管理者向け画面
        H3 admin = new H3("管理者向けメニュー");
        Anchor users = new Anchor("/user_management", "ユーザー管理");
        add(admin, users);
        add(new Paragraph("ユーザー管理を行う画面です。"));
        // IR委員会向けメニュー（CommissionView）の紹介
        H3 commission = new H3("IR委員会向けメニュー");
        Anchor stat = new Anchor("", "統計情報");
        add(commission);
        add(stat, new Paragraph("統計情報が確認できます。"));

        // 教員向け（?）の紹介
        H3 teacher = new H3("教員向けメニュー");
        Anchor grade_assess = new Anchor("questionnaire", "担当した科目の詳細情報");
        add(teacher, grade_assess, new Paragraph("担当した科目の詳細情報を確認できます。"));

        // 学生向け（?）の紹介
        H3 student = new H3("学生向けメニュー");
        Anchor student_assess = new Anchor("", "履修した科目の詳細情報");
        add(student);
        add(student, student_assess, new Paragraph("履修した科目の詳細情報を確認できます"));


    }
}
