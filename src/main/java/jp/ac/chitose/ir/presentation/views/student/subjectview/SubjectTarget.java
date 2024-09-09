package jp.ac.chitose.ir.presentation.views.student.subjectview;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jp.ac.chitose.ir.application.service.student.StudentGradeService;
import jp.ac.chitose.ir.application.service.student.Target;

import java.util.List;

public class SubjectTarget extends VerticalLayout {
    private final Paragraph target;
    private final Paragraph reflection;
    private final String accountId;
    private final StudentGradeService studentGradeService;

    public SubjectTarget(final StudentGradeService studentGradeService, String studentNumber) {
        this.studentGradeService = studentGradeService;
        this.accountId = studentNumber;
        target = createParagraph();
        reflection = createParagraph();
        addComponentToLayout();
    }

    public void addComponentToLayout() {
        add(new H3("目標"), target);
        add(new H3("振り返り"), reflection);
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
            target.setText(subjectTarget.question());
            reflection.setText(subjectTarget.answer());
        }
    }
}
