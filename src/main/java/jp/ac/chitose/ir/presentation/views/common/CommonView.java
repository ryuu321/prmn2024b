package jp.ac.chitose.ir.presentation.views.common;

import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jp.ac.chitose.ir.application.service.class_select.ClassSelect;
import jp.ac.chitose.ir.application.service.student.CourseIdDict;
import jp.ac.chitose.ir.application.service.student.StudentGrade;
import jp.ac.chitose.ir.application.service.student.StudentGradeService;
import jp.ac.chitose.ir.presentation.component.MainLayout;
import jp.ac.chitose.ir.presentation.views.student.RadioButtonValues;
import jp.ac.chitose.ir.presentation.views.student.filter.Filter;
import jp.ac.chitose.ir.presentation.views.student.filter.RadioButtonFilter;
import jp.ac.chitose.ir.presentation.views.student.filterablecomponent.FilterPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

@PermitAll
@PageTitle("common")
@Route(value = "common", layout = MainLayout.class)
public class CommonView extends VerticalLayout {
    private final StudentGradeService studentGradeService;
    private final SelectCommonView selectView;
    private final SubjectCommonView subjectView;
    private final CourseIdDict courseIdDict;

    public CommonView(StudentGradeService studentGradeService, ClassSelect classSelect) {
        this.studentGradeService = studentGradeService;
        this.subjectView = new SubjectCommonView(studentGradeService, classSelect, this);
        this.selectView = new SelectCommonView(studentGradeService, this);
        courseIdDict = studentGradeService.getCourseIdDict();
        add(selectView);
    }

    public void updateView(StudentGrade grade) {
        if(grade == null) {
            add(selectView);
            remove(subjectView);
        }
        else {
            subjectView.updateData(grade);
            add(subjectView);
            remove(selectView);
        }
    }

    public CourseIdDict getCourseIdDict() {
        return courseIdDict;
    }
}
