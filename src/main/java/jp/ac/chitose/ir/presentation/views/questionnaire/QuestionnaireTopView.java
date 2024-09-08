package jp.ac.chitose.ir.presentation.views.questionnaire;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jp.ac.chitose.ir.application.service.class_select.ClassSelect;
import jp.ac.chitose.ir.application.service.class_select.QuestionDescribe;
import jp.ac.chitose.ir.application.service.class_select.QuestionMatters;
import jp.ac.chitose.ir.application.service.class_select.QuestionnaireGraph;
import jp.ac.chitose.ir.application.service.questionnaire.QuestionnaireService;
import jp.ac.chitose.ir.presentation.component.MainLayout;
import jp.ac.chitose.ir.presentation.component.scroll.ScrollManager;

// アンケートのTop画面
@PageTitle("QuestionnaireTop")
@Route(value = "/questionnaire", layout = MainLayout.class)
@PermitAll
public class QuestionnaireTopView extends VerticalLayout {
    private QuestionnaireService questionnaireService;
    private QuestionnaireGrid questionnaireGrid;
    private ClassSelect classSelect;
    private ScrollManager scrollManager;
    private QuestionnaireGraph questionnaireGraph;
    private QuestionMatters questionMatters;
    private jp.ac.chitose.ir.application.service.class_select.QuestionnaireGrid questionGrid;
    private QuestionDescribe questionDescribe;


    public QuestionnaireTopView(QuestionnaireService questionnaireService,ClassSelect classSelect) {
        VerticalLayout layout = new VerticalLayout();



        this.classSelect = classSelect;
        this.questionnaireService = questionnaireService;
        this.scrollManager = new ScrollManager();
        this.questionnaireGraph=new QuestionnaireGraph(classSelect);
        this.questionMatters = new QuestionMatters(classSelect, scrollManager);
        this.questionGrid = new jp.ac.chitose.ir.application.service.class_select.QuestionnaireGrid(classSelect);
        this.questionDescribe = new QuestionDescribe(classSelect);

        questionnaireGrid = new QuestionnaireGrid(questionnaireService,classSelect);
        layout.add(questionnaireGrid);

        add(layout);

    }
}
