package jp.ac.chitose.ir.presentation.views.class_select;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jp.ac.chitose.ir.application.service.class_select.ClassSelect;
import jp.ac.chitose.ir.application.service.class_select.QuestionMatters;
import jp.ac.chitose.ir.application.service.class_select.QuestionnaireGraph;
import jp.ac.chitose.ir.application.service.class_select.ReviewQPOJFICHKVJBDescription;
import jp.ac.chitose.ir.presentation.component.MainLayout;
import jp.ac.chitose.ir.presentation.component.scroll.ScrollManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@PageTitle("class_QPOJFICHKVJB")
@Route(value = "class_select/QPOJFICHKVJB", layout = MainLayout.class)
@PermitAll

public class QPOJFICHKVJBView extends VerticalLayout implements AfterNavigationObserver {
    private ClassSelect classSelect;
    private ScrollManager scrollManager;
    private QuestionnaireGraph questionnaireGraph;
    private QuestionMatters questionMatters;

    public QPOJFICHKVJBView(ClassSelect classSelect) {
        this.classSelect = classSelect;
        this.scrollManager = new ScrollManager();
        this.questionnaireGraph=new QuestionnaireGraph(classSelect);
        this.questionMatters = new QuestionMatters(classSelect, scrollManager);
        VerticalLayout layout = new VerticalLayout();

        String subject_id = "170Fg";  // 実際のsubject_idに置き換えてください
        init1(subject_id);

        index();


        for (int i = 0; i < 11; i++) {


            Button scrollToTitleButton = new Button("Scroll to Q" + (i + 4));
            String id = "title-" + i + 4;
            scrollToTitleButton.addClickListener(event -> scrollManager.scrollToComponentById(id));
            layout.add(scrollToTitleButton);
        }


        layout.getStyle().set("padding", "40px");
        for (int i = 0; i < 11; i++) {
            if(i == 3 || i == 6){
                layout.add(questionMatters.generateQuestionMatters(3,subject_id));
                layout.add(grid(subject_id));;//自由記述
                continue;}

            layout.add(questionMatters.generateQuestionMatters(i,subject_id));//example
            layout.add(questionnaireGraph.generateQuestionnaireGraph(i+4,subject_id).getGraph());
            add(layout);


        }

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


    private void index() {

        add(new H3("質問項目一覧(未実装)"));



    }




    /**
     * 自由記述解答の表示example
     */

    private Component grid(String subject_id) {


        Grid<String> grid = new Grid<>(String.class, false);
        //grid.addColumn(ReviewQPOJFICHKVJBDescription::q17).setHeader("Q17");
        grid.addColumn(description -> String.valueOf(description)).setHeader("Q7");

        //List<String> people = (List<String>) classSelect.getReviewQPOJFICHKVJBDescription(subject_id).data().get(0).q7().values();
        Collection<String> peopleCollection = classSelect.getReviewQPOJFICHKVJBDescription(subject_id).data().get(0).q7().values();
        List<String> people = new ArrayList<>(peopleCollection);

        System.out.println("aaaa"+people);
        grid.setItems(people);

        return grid;

        //質問項目一覧の表示
        //クリックすると該当科目まで遷移


    }



}