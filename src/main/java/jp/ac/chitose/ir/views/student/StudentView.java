package jp.ac.chitose.ir.views.student;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jp.ac.chitose.ir.service.student.StudentGrade;
import jp.ac.chitose.ir.service.student.StudentService;
import jp.ac.chitose.ir.views.MainLayout;

@PageTitle("GradeStudent")
@Route(value = "grade/student", layout = MainLayout.class)
public class StudentView extends VerticalLayout {
    private final StudentService studentService;
    private String studentSchoolYear;
    private ComboBox<String> subjectComboBox;
    private TextField textField;;
    private RadioButtonGroup<String> schoolYearsRadioButton;
    private RadioButtonGroup<String> departmentsRadioButton;
    private GPALayout gpaLayout;
    private SubjectLayout subjectLayout;

    public StudentView(StudentService studentService) {
        this.studentService = studentService;
        textFieldInitialize();
        schoolYearsRadioButtonInitialize();
        departmentsRadioButtonInitialize();
        subjectComboBoxInitialize();
        add(textField, new H1("Student"), new Paragraph("説明"));
        add(new H3("学年"), schoolYearsRadioButton, new H3("学科"), departmentsRadioButton, subjectComboBox);
    }

    // 学籍番号を入力するためのテキストフィールド、データベースで個人識別できるようになれば、必要なくなる。
    // その場合、このメソッドでやっていた処理は、セットアップなどでやることになる
    private void textFieldInitialize() {
        textField = new TextField();
        textField.addValueChangeListener(valueChangeEvent -> {
            if(valueChangeEvent.getValue() == null || valueChangeEvent.getValue().isEmpty()) return;
            studentSchoolYear = studentService.getStudentSchoolYear(textField.getValue()).data().get(0).学年();
            subjectComboBox.setItems(studentService.getStudentNumberGrades(valueChangeEvent.getValue()).data().stream()
                    .map(StudentGrade::科目名).toList());
            gpaLayout = new GPALayout(studentService, studentSchoolYear, subjectComboBox, valueChangeEvent.getValue());
            subjectLayout = new SubjectLayout(studentService);
            add(gpaLayout);
        });
    }

    // コンボボックスの科目を学年で絞り込む機能の初期化 addValueChangeListenerに変更があったときの処理を書き込む予定
    private void schoolYearsRadioButtonInitialize() {
        schoolYearsRadioButton = new RadioButtonGroup<>("", "全体", "1年生", "2年生", "3年生", "4年生", "修士1年生", "修士2年生");
        schoolYearsRadioButton.setValue("全体");
        schoolYearsRadioButton.addValueChangeListener(valueChangeEvent -> {
        });
        add(schoolYearsRadioButton);
    }

    // コンボボックスの科目を学科で絞り込む機能の初期化 addValueChangeListenerに変更があったときの処理を書き込む予定
    private void departmentsRadioButtonInitialize() {
        departmentsRadioButton = new RadioButtonGroup<>("", "全体", "応用科学生物学科", "電子光工学科", "情報システム工学科", "理工学研究科");
        departmentsRadioButton.setValue("全体");
        departmentsRadioButton.addValueChangeListener(valueChangeEvent -> {
        });
    }

    // 科目選択のコンボボックスの初期化 valueChangeEventによって、GPA・科目の画面を切り替える 科目の時はその画面を作る
    private void subjectComboBoxInitialize() {
        subjectComboBox = new ComboBox<>("科目名");
        subjectComboBox.setWidth("600px");
        subjectComboBox.setPlaceholder("GPAのグラフを表示しています。選んだ科目のグラフに切り替わります。");
        subjectComboBox.setClearButtonVisible(true);
        subjectComboBox.addValueChangeListener(valueChangeEvent -> {
            if(valueChangeEvent.getValue() == null || valueChangeEvent.getValue().isEmpty()) {
                add(gpaLayout);
                remove(subjectLayout);
            } else {
                subjectLayout.create(textField.getValue(), valueChangeEvent.getValue());
                add(subjectLayout);
                remove(gpaLayout);
            }
        });
    }
}