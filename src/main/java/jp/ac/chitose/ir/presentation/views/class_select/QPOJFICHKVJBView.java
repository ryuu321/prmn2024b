package jp.ac.chitose.ir.presentation.views.class_select;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jp.ac.chitose.ir.application.service.class_select.*;
import jp.ac.chitose.ir.presentation.component.SubLayout;
import jp.ac.chitose.ir.presentation.component.scroll.ScrollManager;

import java.util.List;

@PageTitle("class_QPOJFICHKVJB")
@Route(value = "class_select/Top/QPOJFICHKVJB", layout = SubLayout.class)
@PermitAll

public class QPOJFICHKVJBView extends VerticalLayout {
    private ClassSelect classSelect;
    private ScrollManager scrollManager;
    private QuestionnaireGraph questionnaireGraph;
    private QuestionMatters questionMatters;
    private QuestionnaireGrid questionnaireGrid;
    private QuestionDescribe questionDescribe;

    public QPOJFICHKVJBView(ClassSelect classSelect) {
        this.classSelect = classSelect;
        this.scrollManager = new ScrollManager();
        this.questionnaireGraph=new QuestionnaireGraph(classSelect);
        this.questionMatters = new QuestionMatters(classSelect, scrollManager);
        this.questionnaireGrid = new QuestionnaireGrid(classSelect);
        this.questionDescribe = new QuestionDescribe(classSelect);
        VerticalLayout layout = new VerticalLayout();

        String subject_id = "AdiXne";  // 実際のsubject_idに置き換えてください
        init1(subject_id);

        layout.getStyle().set("padding", "40px");
        var classTests = classSelect.getClassQPOJFICHKVJB(subject_id).data();
        int flag = classTests.get(0).Flag();

        for (int i = 0; i < 11; i++) {
            if(i == 3 && flag == 1){
                layout.add(questionMatters.generateQuestionMatters(3,subject_id));
                layout.add(questionnaireGrid.generateGrid(i,subject_id));;//自由記述
                continue;}

            layout.add(questionMatters.generateQuestionMatters(i,subject_id));//example
            layout.add(questionnaireGraph.generateQuestionnaireGraph(i+4,subject_id).getGraph());
            layout.add(questionDescribe.getStatics(i+4,subject_id));

        }

        layout.add(questionMatters.generateQuestionMatters(13,subject_id));
        layout.add(questionnaireGrid.generateGrid(13,subject_id));//自由記述
        layout.add(questionMatters.generateQuestionMatters(14,subject_id));
        layout.add(questionnaireGrid.generateGrid(14,subject_id));
        layout.add(questionMatters.generateQuestionMatters(15,subject_id));
        layout.add(questionnaireGrid.generateGrid(15,subject_id));

        add(layout);


    }


/* //
    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        getUI().ifPresent(ui -> ui.getPage().executeJs(
                        "return window.location.hash.substring(1);")
                .then(value -> {
                    // フラグメントを取得
                    String fragment = value.asString();
                    fragment = fragment.replace("\"", ""); // クオートを削除

                    // デバッグ用にフラグメントを出力
                    System.out.println("Fragment: " + fragment);
                    // フラグメントが存在する場合、スムーズスクロールを実行
                    if (fragment != null && !fragment.isEmpty()) {
                        ui.getPage().executeJs(
                                "document.addEventListener('DOMContentLoaded', function() { " +
                                        "const element = document.getElementById($0); " +
                                        "if (element) { " +
                                        "  element.scrollIntoView({ behavior: 'smooth' }); " +
                                        "} else { " +
                                        "  console.warn('Element not found for ID: ' + $0); " +
                                        "}" +
                                        "});", fragment);
                    }
                }));
    }
*/

    private void init1(String subject_id) {
        List<ReviewQPOJFICHKVJBDescription> review_data = classSelect.getReviewQPOJFICHKVJBDescription(subject_id).data();
        String subject_Title = review_data.get(0).科目名().values().iterator().next();;
        String subject_teacher = review_data.get(0).担当者().values().iterator().next();
        add(new H1("科目名:"+ subject_Title));
        add(new H3("科目担当:"+ subject_teacher));
    }

    /*private void index() {
        add(new H3("質問項目一覧(未実装)"));

    }*/
}