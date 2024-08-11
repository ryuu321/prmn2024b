package jp.ac.chitose.ir.presentation.views.class_select;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jp.ac.chitose.ir.application.service.class_select.ClassSelect;
import jp.ac.chitose.ir.application.service.class_select.QuestionMatters;
import jp.ac.chitose.ir.application.service.class_select.QuestionnaireGraph;
import jp.ac.chitose.ir.presentation.component.MainLayout;
import jp.ac.chitose.ir.presentation.component.scroll.ScrollManager;

@PageTitle("class_QPOJFICHKVJB")
@Route(value = "class_select/QPOJFICHKVJB", layout = MainLayout.class)
@PermitAll

public class QPOJFICHKVJBView extends VerticalLayout {
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

        //init1();

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
                questionMatters.generateQuestionMatters(3,subject_id);
               // layout.add(grid(reviewQPOJFICHKVJBDescription));;//自由記述
                continue;}

            layout.add(questionMatters.generateQuestionMatters(i,subject_id));//example
            //layout.add(questionnaireGraph.generateQuestionnaireGraph(i+4,qpojfichkvjb).getGraph());
            add(layout);


        }

    }

/*
    private void init1() {
        List<ReviewQPOJFICHKVJBDescription> review_data = classSelect.getReviewQPOJFICHKVJBDescription("O135xW").data();
        String subject_Title = String.valueOf(review_data.get(0).科目名());
        String subject_teacher = String.valueOf(review_data.get(0).担当者());
        add(new H1("科目名:"+ subject_Title));
        add(new H3("科目担当:"+ subject_teacher));
    }
*/

    private void index() {

        add(new H3("質問項目一覧(未実装)"));



    }




    /**
     * 自由記述解答の表示example
     */
    /*
    private Component grid(ReviewQPOJFICHKVJBDescription reviewQPOJFICHKVJBDescription) {


        Grid<jp.ac.chitose.ir.application.service.class_select.ReviewQPOJFICHKVJBDescription> grid = new Grid<>(jp.ac.chitose.ir.application.service.class_select.ReviewQPOJFICHKVJBDescription.class, false);
        grid.addColumn(ReviewQPOJFICHKVJBDescription::q19).setHeader("Q7");
        List<jp.ac.chitose.ir.application.service.class_select.ReviewQPOJFICHKVJBDescription> people = classSelect.getReviewQPOJFICHKVJBDescription(reviewQPOJFICHKVJBDescription.q7()).data();
        grid.setItems(people);

        return grid;

        //質問項目一覧の表示
        //クリックすると該当科目まで遷移


    }*/



}