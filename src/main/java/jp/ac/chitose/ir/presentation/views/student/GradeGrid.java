package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.function.SerializablePredicate;
import jp.ac.chitose.ir.application.service.student.StudentGrade;
import jp.ac.chitose.ir.application.service.student.StudentService;

public class GradeGrid extends VerticalLayout {
    private RadioButtonGroup<String> schoolYearsRadioButton;
    private RadioButtonGroup<String> departmentsRadioButton;
    private RadioButtonGroup<String> subjectTypesRadioButton;
    private RadioButtonGroup<String> gradesRadioButton;
    private GridListDataView<StudentGrade> gridListDataView;

    // コンストラクタ
    public GradeGrid(StudentService studentService, ComboBox<StudentGrade> subjectComboBox, String textFieldValue) {
        initializeRadioButtons();
        Grid<StudentGrade> grid = initializeGrid(studentService, textFieldValue, subjectComboBox);
        addComponentsToLayout(grid);
    }

    // ラジオボタンの初期化メソッドをまとめて呼び出す
    private void initializeRadioButtons() {
        initializeSchoolYearsRadioButton();
        initializeDepartmentsRadioButton();
        initializeSubjectTypesRadioButton();
        initializeGradesRadioButton();
    }

    // 学年ラジオボタンの初期化
    private void initializeSchoolYearsRadioButton() {
        schoolYearsRadioButton = new RadioButtonGroup<>();
        schoolYearsRadioButton.setItems("全体", "1年生", "2年生", "3年生", "4年生", "修士1年生", "修士2年生");
        schoolYearsRadioButton.setValue("全体");
        schoolYearsRadioButton.addValueChangeListener(event -> applyFilters());
    }

    // 学部・学科ラジオボタンの初期化
    private void initializeDepartmentsRadioButton() {
        departmentsRadioButton = new RadioButtonGroup<>();
        departmentsRadioButton.setItems("全体", "理工学部", "応用化学生物学科", "電子光工学科", "情報システム工学科", "理工学研究科");
        departmentsRadioButton.setValue("全体");
        departmentsRadioButton.addValueChangeListener(event -> applyFilters());
    }

    // 必選別ラジオボタンの初期化
    private void initializeSubjectTypesRadioButton() {
        subjectTypesRadioButton = new RadioButtonGroup<>();
        subjectTypesRadioButton.setItems("全体", "必修", "選択必修", "選択");
        subjectTypesRadioButton.setValue("全体");
        subjectTypesRadioButton.addValueChangeListener(event -> applyFilters());
    }

    // 成績評価ラジオボタンの初期化
    private void initializeGradesRadioButton() {
        gradesRadioButton = new RadioButtonGroup<>();
        gradesRadioButton.setItems("全体", "不可", "可", "良", "優", "秀");
        gradesRadioButton.setValue("全体");
        gradesRadioButton.addValueChangeListener(event -> applyFilters());
    }

    // グリッドの初期化
    private Grid<StudentGrade> initializeGrid(StudentService studentService, String textFieldValue, ComboBox<StudentGrade> subjectComboBox) {
        Grid<StudentGrade> grid = new Grid<>(StudentGrade.class, false);
        addColumnsToGrid(grid);
        grid.setWidthFull();
        grid.setAllRowsVisible(true);
        grid.addItemClickListener(event -> subjectComboBox.setValue(event.getItem()));
        gridListDataView = grid.setItems(studentService.getStudentNumberGrades(textFieldValue).data());
        return grid;
    }

    // グリッドにカラムを追加
    private void addColumnsToGrid(Grid<StudentGrade> grid) {
        grid.addColumn(StudentGrade::科目名).setHeader("科目名").setWidth("30%");
        grid.addColumn(data -> data.対象学年() + "年生").setHeader("対象学年");
        grid.addColumn(data -> changeDepartmentValue(data.対象学科())).setHeader("対象学科");
        grid.addColumn(StudentGrade::必選別).setHeader("必選別");
        grid.addColumn(StudentGrade::科目の単位数).setHeader("単位数");
        grid.addColumn(StudentGrade::成績評価).setHeader("成績評価");
    }

    // コンポーネントをレイアウトに追加
    private void addComponentsToLayout(Grid<StudentGrade> grid) {
        add(new H3("学年"), schoolYearsRadioButton);
        add(new H3("学部・学科"), departmentsRadioButton);
        add(new H3("必選別"), subjectTypesRadioButton);
        add(new H3("成績評価"), gradesRadioButton);
        add(grid);
    }

    // フィルタを適用
    private void applyFilters() {
        gridListDataView.setFilter(new Filter());
    }

    // データベースの学科表記をラジオボタンの表記に変更
    private String changeDepartmentValue(String department) {
        if ("理工学部 情報ｼｽﾃﾑ工学科".equals(department)) return "情報システム工学科";
        return department;
    }

    // フィルタクラス
    private class Filter implements SerializablePredicate<StudentGrade> {
        @Override
        public boolean test(StudentGrade studentGrade) {
            boolean schoolYearMatches = "全体".equals(schoolYearsRadioButton.getValue()) || (studentGrade.対象学年() + "年生").equals(schoolYearsRadioButton.getValue());
            boolean departmentMatches = "全体".equals(departmentsRadioButton.getValue()) || changeDepartmentValue(studentGrade.対象学科()).equals(departmentsRadioButton.getValue());
            boolean subjectTypeMatches = "全体".equals(subjectTypesRadioButton.getValue()) || studentGrade.必選別().equals(subjectTypesRadioButton.getValue());
            boolean gradeMatches = "全体".equals(gradesRadioButton.getValue()) || studentGrade.成績評価().equals(gradesRadioButton.getValue());
            return schoolYearMatches && departmentMatches && subjectTypeMatches && gradeMatches;
        }
    }
}
