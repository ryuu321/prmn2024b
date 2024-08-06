package jp.ac.chitose.ir.presentation.views.top;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
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
        H1 title = new H1("IRポータル（仮） にようこそ");
        add(title);

        // 成績情報ページ（StudentView）の紹介
        Anchor grade = new Anchor("/grade/student", "成績情報");
        grade.getElement().setAttribute("target", "");
        add(new H2(grade));
        add(new Paragraph("成績情報を確認できます。"));

        // 成績統計ページ（CommissionView）の紹介
        Anchor gradestat = new Anchor("commission", "成績情報");
        add(new H2(gradestat));
        add(new Paragraph("成績に関する詳細な統計情報が確認できます。"));

        // アンケートページ（ClassSelectView?）の紹介
        Anchor questionnaire = new Anchor("questionnaire", "アンケート");
        add(new H2(questionnaire));
        add(new Paragraph("各種アンケートの回答結果を確認できます。"));
    }
}
