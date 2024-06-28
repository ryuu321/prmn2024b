package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.dataview.ComboBoxListDataView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jp.ac.chitose.ir.application.service.student.StudentGrade;
import jp.ac.chitose.ir.application.service.student.StudentService;
import jp.ac.chitose.ir.presentation.component.MainLayout;

@PermitAll
@PageTitle("GradeStudent")
@Route(value = "grade/student", layout = MainLayout.class)
public class StudentView extends VerticalLayout {
    private final StudentService studentService;
    private final RadioButtonGroup<String> schoolYearsRadioButton;
    private final RadioButtonGroup<String> departmentsRadioButton;
    private final ComboBox<StudentGrade> subjectComboBox;
    private final ComboBoxListDataView<StudentGrade> subjectComboBoxDataView;
    private final TextField textField;
    private GPALayout gpaLayout;
    private SubjectLayout subjectLayout;
    private static final String[] SCHOOL_YEARS = new String[]{"全体", "1年生", "2年生", "3年生", "4年生", "修士1年生", "修士2年生"};
    private static final String[] DEPARTMENTS = new String[]{"全体", "応用科学生物学科", "電子光工学科", "情報システム工学科", "理工学研究科"};

    public StudentView(StudentService studentService) {
        this.studentService = studentService;

        subjectComboBox = new ComboBox<>("科目名");
        initializeSubjectComboBox(subjectComboBox);
        subjectComboBoxDataView = subjectComboBox.getListDataView();

        schoolYearsRadioButton = createRadioButton(SCHOOL_YEARS);
        departmentsRadioButton = createRadioButton(DEPARTMENTS);

        textField = new TextField();
        textFieldAddChangeListener(textField);

        addComponentsToLayout();
    }

    @SafeVarargs
    private <T> RadioButtonGroup<T> createRadioButton(T... args) {
        RadioButtonGroup<T> radioButtonGroup = new RadioButtonGroup<>();
        radioButtonGroup.setItems(args);
        radioButtonGroup.setValue(args[0]);
        radioButtonGroup.addValueChangeListener(valueChangeEvent -> applyFilters(subjectComboBoxDataView));
        return radioButtonGroup;
    }

    private void applyFilters(ComboBoxListDataView<StudentGrade> subjectComboBoxDataView) {
        subjectComboBoxDataView.removeFilters();
        subjectComboBoxDataView.addFilter(this::filter);
    }

    private boolean filter(StudentGrade studentGrade) {
        return (schoolYearsRadioButton.getValue().equals("全体") && departmentsRadioButton.getValue().equals("全体"))
                || (schoolYearsRadioButton.getValue().equals("全体") && departmentsRadioButton.getValue().equals(studentGrade.department()))
                || (schoolYearsRadioButton.getValue().equals(studentGrade.schoolYear()) && departmentsRadioButton.getValue().equals("全体"))
                || (schoolYearsRadioButton.getValue().equals(studentGrade.schoolYear()) && departmentsRadioButton.getValue().equals(studentGrade.department()));
    }

    private void addComponentsToLayout() {
        add(textField, new H1("Student"), new Paragraph("説明"));
        add(new H3("学年"), schoolYearsRadioButton, new H3("学科"), departmentsRadioButton, subjectComboBox);
    }

    // データベースから生徒を識別できる値を持ってこれるようになったら、消す
    private void textFieldAddChangeListener(TextField textField) {
        textField.addValueChangeListener(this::setStudentNumberEvent);
    }

    private void setStudentNumberEvent(AbstractField.ComponentValueChangeEvent<TextField, String> valueChangeEvent) {
        String value = valueChangeEvent.getValue();
        if (value == null || value.isEmpty()) return;
        String studentSchoolYear = studentService.getStudentSchoolYear(value).data().get(0).学年();
        subjectComboBox.setItems(studentService.getStudentNumberGrades(value).data());
        gpaLayout = new GPALayout(studentService, studentSchoolYear, subjectComboBox, value);
        subjectLayout = new SubjectLayout(studentService);
        add(gpaLayout);
    }

    private void initializeSubjectComboBox(ComboBox<StudentGrade> subjectComboBox) {
        subjectComboBox.setLabel("科目名");
        subjectComboBox.setItemLabelGenerator(StudentGrade::科目名);
        subjectComboBox.setWidth("40%");
        subjectComboBox.setPlaceholder("GPAのグラフを表示しています。選んだ科目のグラフに切り替わります。");
        subjectComboBox.setClearButtonVisible(true);
        subjectComboBox.addValueChangeListener(this::changeLayout);

    }

    private void changeLayout(AbstractField.ComponentValueChangeEvent<ComboBox<StudentGrade>, StudentGrade> valueChangeEvent) {
        if (valueChangeEvent.getValue() == null) {
            remove(subjectLayout);
            add(gpaLayout);
        } else {
            subjectLayout.update(textField.getValue(), valueChangeEvent.getValue().科目名());
            remove(gpaLayout);
            add(subjectLayout);
        }
    }
}
