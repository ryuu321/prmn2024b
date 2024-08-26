package jp.ac.chitose.ir.presentation.views.class_select;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jp.ac.chitose.ir.application.service.class_select.*;
import jp.ac.chitose.ir.presentation.component.MainLayout;
import jp.ac.chitose.ir.presentation.component.scroll.ScrollManager;

import java.io.IOException;
import java.util.List;

@PageTitle("class_QPOJFICHKVJB")
@Route(value = "class_select/QPOJFICHKVJB", layout = MainLayout.class)
@PermitAll

public class QPOJFICHKVJBView extends VerticalLayout implements AfterNavigationObserver {
    private ClassSelect classSelect;
    private ScrollManager scrollManager;
    private QuestionnaireGraph questionnaireGraph;
    private QuestionMatters questionMatters;
    private QuestionnaireGrid questionnaireGrid;
    private QuestionDescribe questionDescribe;

    public QPOJFICHKVJBView(ClassSelect classSelect) throws IOException {
        this.classSelect = classSelect;
        this.scrollManager = new ScrollManager();
        this.questionnaireGraph=new QuestionnaireGraph(classSelect);
        this.questionMatters = new QuestionMatters(classSelect, scrollManager);
        this.questionnaireGrid = new QuestionnaireGrid(classSelect);
        this.questionDescribe = new QuestionDescribe(classSelect);
        VerticalLayout layout = new VerticalLayout();

        String subject_id = "170Fg";  // 実際のsubject_idに置き換えてください
        init1(subject_id);

        for (int i = 0; i < 11; i++) {


            Button scrollToTitleButton = new Button("Scroll to Q" + (i + 4));
            String id = "title-" + i + 4;
            scrollToTitleButton.addClickListener(event -> scrollManager.scrollToComponentById(id));
            layout.add(scrollToTitleButton);
        }


        layout.getStyle().set("padding", "40px");
        for (int i = 0; i < 11; i++) {
            if(i == 3){
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

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        // URLからフラグメントを取得
        System.out.println("after");
        String locationPath = event.getLocation().getPathWithQueryParameters();
        String fragment = null;
        System.out.println(locationPath);
        int hashIndex = locationPath.indexOf('#');
        System.out.println(hashIndex);
        if (hashIndex != -1) {
            fragment = locationPath.substring(hashIndex + 1); // フラグメント部分を取得
            System.out.println(fragment);
        }
        if (fragment != null && !fragment.isEmpty()) {
            scrollManager.scrollToComponentById(fragment);
            System.out.println(fragment);
        }
    }


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