package jp.ac.chitose.ir.presentation.views.questionnaire;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jp.ac.chitose.ir.application.service.questionnaire.QuestionnaireService;
import jp.ac.chitose.ir.presentation.component.MainLayout;

// アンケートのTop画面
@PageTitle("QuestionnaireTop")
@Route(value = "/questionnaire", layout = MainLayout.class)
@PermitAll
public class QuestionnaireTopView extends VerticalLayout {
    private QuestionnaireService questionnaireService;
    private QuestionnaireGrid questionnaireGrid;

    public QuestionnaireTopView(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
        questionnaireGrid = new QuestionnaireGrid(questionnaireService);
        add(questionnaireGrid);
    }
}
