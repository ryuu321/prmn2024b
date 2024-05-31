package jp.ac.chitose.ir.views.student;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.dataview.ComboBoxListDataView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jp.ac.chitose.ir.service.student.StudentGrade;
import jp.ac.chitose.ir.service.student.StudentService;
import jp.ac.chitose.ir.views.MainLayout;
import jp.ac.chitose.ir.views.component.ErrorNotification;
import jp.ac.chitose.ir.views.component.SuccessNotification;

@PageTitle("GradeStudent")
@Route(value = "grade/student", layout = MainLayout.class)
public class StudentView extends VerticalLayout {
    private final StudentService studentService;
    private String studentSchoolYear;
    private ComboBox<StudentGrade> subjectComboBox;
    private ComboBoxListDataView<StudentGrade> subjectComboBoxDataView;
    private TextField textField;
    private RadioButtonGroup<String> schoolYearsRadioButton;
    private RadioButtonGroup<String> departmentsRadioButton;
    private GPALayout gpaLayout;
    private SubjectLayout subjectLayout;

    public StudentView(StudentService studentService) {
        this.studentService = studentService;
        initializeComponents();
        addComponentsToLayout();
    }

    // 各コンポーネントの初期化
    private void initializeComponents() {
        initializeTextField();
        initializeSchoolYearsRadioButton();
        initializeDepartmentsRadioButton();
        initializeSubjectComboBox();
    }

    // コンポーネントをレイアウトに追加
    private void addComponentsToLayout() {
        add(textField, new H1("Student"), new Paragraph("説明"));
        add(new H3("学年"), schoolYearsRadioButton, new H3("学科"), departmentsRadioButton, subjectComboBox);
    }

    // 学籍番号入力用のテキストフィールドの初期化
    // データベースから生徒を識別できる値を持ってこれるようになったら、消す
    private void initializeTextField() {
        textField = new TextField();
        textField.addValueChangeListener(valueChangeEvent -> {
            String value = valueChangeEvent.getValue();
            if (value == null || value.isEmpty()) return;
            studentSchoolYear = studentService.getStudentSchoolYear(value).data().get(0).学年();
            subjectComboBox.setItems(studentService.getStudentNumberGrades(value).data());
            subjectComboBoxDataView = subjectComboBox.getListDataView();
            gpaLayout = new GPALayout(studentService, studentSchoolYear, subjectComboBox, value);
            subjectLayout = new SubjectLayout(studentService);
            add(gpaLayout);
        });
    }

    // 学年ラジオボタンの初期化
    private void initializeSchoolYearsRadioButton() {
        schoolYearsRadioButton = new RadioButtonGroup<>();
        schoolYearsRadioButton.setItems("全体", "1年生", "2年生", "3年生", "4年生", "修士1年生", "修士2年生");
        schoolYearsRadioButton.setValue("全体");
        schoolYearsRadioButton.addValueChangeListener(valueChangeEvent -> applyFilters());
    }

    // 学科ラジオボタンの初期化
    private void initializeDepartmentsRadioButton() {
        departmentsRadioButton = new RadioButtonGroup<>();
        departmentsRadioButton.setItems("全体", "応用科学生物学科", "電子光工学科", "情報システム工学科", "理工学研究科");
        departmentsRadioButton.setValue("全体");
        departmentsRadioButton.addValueChangeListener(valueChangeEvent -> applyFilters());
    }

    // ラジオボタンのフィルタ適用メソッド
    private void applyFilters() {
        subjectComboBoxDataView.removeFilters();
        subjectComboBoxDataView.addFilter((SerializablePredicate<StudentGrade>) s -> {
            if (schoolYearsRadioButton.getValue().equals("全体")) return true;
            return schoolYearsRadioButton.getValue().equals(changeSchoolYearValue(s.対象学年()));
        });
        subjectComboBoxDataView.addFilter((SerializablePredicate<StudentGrade>) s -> {
            if (departmentsRadioButton.getValue().equals("全体")) return true;
            return departmentsRadioButton.getValue().equals(changeDepartmentValue(s.対象学科()));
        });
    }

    // 学科名をラジオボタンの表記に合わせる
    private String changeDepartmentValue(String department) {
        switch (department) {
            case "理工学部 情報ｼｽﾃﾑ工学科":
                return "情報システム工学科";
            default:
                return department;
        }
    }

    // 学年をラジオボタンの表記に合わせる
    private String changeSchoolYearValue(int schoolYear) {
        switch (schoolYear) {
            case 1:
                return "1年生";
            case 2:
                return "2年生";
            case 3:
                return "3年生";
            case 4:
                return "4年生";
            default:
                return "";
        }
    }

    // 科目選択用のコンボボックスの初期化
    private void initializeSubjectComboBox() {
        subjectComboBox = new ComboBox<>("科目名");
        subjectComboBox.setItemLabelGenerator(StudentGrade::科目名);
        subjectComboBox.setWidth("40%");
        subjectComboBox.setPlaceholder("GPAのグラフを表示しています。選んだ科目のグラフに切り替わります。");
        subjectComboBox.setClearButtonVisible(true);
        subjectComboBoxDataView = subjectComboBox.getListDataView();
        subjectComboBox.addValueChangeListener(valueChangeEvent -> {
            if (valueChangeEvent.getValue() == null) {
                new ErrorNotification("GPAのレイアウトに変更完了");
                remove(subjectLayout);
                add(gpaLayout);
            } else {
                new SuccessNotification("教科ごとのレイアウトに変更完了");
                subjectLayout.create(textField.getValue(), valueChangeEvent.getValue().科目名());
                remove(gpaLayout);
                add(subjectLayout);
            }
        });
    }
}
