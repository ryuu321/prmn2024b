package jp.ac.chitose.ir.presentation.views.student.subjectview;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jp.ac.chitose.ir.application.service.student.StudentGradeService;
import jp.ac.chitose.ir.application.service.student.Target;

import java.util.List;

public class SubjectTarget extends VerticalLayout {
    private final H3 targetQuestion;
    private final H3 reviewQuestion;
    private final Paragraph targetAnswer;
    private final Paragraph reviewAnswer;
    private final String accountId;
    private final StudentGradeService studentGradeService;

    public SubjectTarget(final StudentGradeService studentGradeService, String studentNumber) {
        this.studentGradeService = studentGradeService;
        this.accountId = studentNumber;
        targetQuestion = new H3();
        reviewQuestion = new H3();
        targetAnswer = createParagraph();
        reviewAnswer = createParagraph();
        addComponentToLayout();
    }

    public void addComponentToLayout() {
        add(targetQuestion, targetAnswer);
        add(reviewQuestion, reviewAnswer);
    }

    private Paragraph createParagraph() {
        Paragraph paragraph = new Paragraph();
        paragraph.getStyle().set("margin-left", "2em");
        return paragraph;
    }

    public void update(String courseId) {
        List<Target> subjectTargets = studentGradeService.getSubjectTarget(accountId, courseId).data();
        if(!subjectTargets.isEmpty()) {
            Target subjectTarget = subjectTargets.get(0);
            targetQuestion.setText("Q " + subjectTarget.target_question_1());
            targetAnswer.setText(subjectTarget.target_answer_1());
            reviewQuestion.setText("Q " + subjectTarget.review_question_1());
            reviewAnswer.setText(subjectTarget.review_answer_1());
        }
    }
}
