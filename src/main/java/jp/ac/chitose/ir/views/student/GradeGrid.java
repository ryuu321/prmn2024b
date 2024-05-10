package jp.ac.chitose.ir.views.student;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.function.SerializablePredicate;
import jp.ac.chitose.ir.service.student.StudentService;
import jp.ac.chitose.ir.service.student.StudentTable;

public class GradeGrid extends VerticalLayout {
    private StudentService studentService;
    private RadioButtonGroup<String> schoolYearsRadioButton;
    private RadioButtonGroup<String> departmentsRadioButton;
    private RadioButtonGroup<String> subjectTypesRadioButton;
    private RadioButtonGroup<String> gradeRadioButton;
    private Grid<StudentTable> grid;
    private GridListDataView<StudentTable> gridListDataView;
    public GradeGrid(StudentService studentService) {
        this.studentService = studentService;
    }

    public void create(String textField, ComboBox comboBox) {
        removeAll();
        schoolYearsRadioButton = new RadioButtonGroup<>("", "全体", "1年生", "2年生", "3年生", "4年生", "修士1年生", "修士2年生");
        schoolYearsRadioButton.setValue("全体");
        schoolYearsRadioButton.addValueChangeListener(valueChangeEvent -> gridListDataView.setFilter(new Filter()));
        departmentsRadioButton = new RadioButtonGroup<>("", "全体", "理工学部", "応用化学生物学科", "電子光工学科", "情報システム工学科", "理工学研究科");
        departmentsRadioButton.setValue("全体");
        departmentsRadioButton.addValueChangeListener(valueChangeEvent -> gridListDataView.setFilter(new Filter()));
        subjectTypesRadioButton = new RadioButtonGroup<>("", "全体", "必修", "選択必修", "選択");
        subjectTypesRadioButton.setValue("全体");
        subjectTypesRadioButton.addValueChangeListener(valueChangeEvent -> gridListDataView.setFilter(new Filter()));
        gradeRadioButton = new RadioButtonGroup<>("", "全体", "不可", "可", "良", "優", "秀");
        gradeRadioButton.setValue("全体");
        gradeRadioButton.addValueChangeListener(valueChangeEvent -> gridListDataView.setFilter(new Filter()));
        grid = new Grid<>(StudentTable.class, false);
        Grid.Column<StudentTable> subjectColumn = grid.addColumn(StudentTable::科目名);
        Grid.Column<StudentTable> schoolYearColumn = grid.addColumn(data -> data.対象学年() + "年生");
        Grid.Column<StudentTable> departmentColumn = grid.addColumn(data -> changeDepartmentValue(data.対象学科()));
        Grid.Column<StudentTable> subjectTypeColumn = grid.addColumn(StudentTable::必選別);
        Grid.Column<StudentTable> gradeColumn = grid.addColumn(StudentTable::成績評価);
        gridListDataView = grid.setItems(studentService.getStudentTable(textField).data());
        grid.setWidthFull();
        grid.setAllRowsVisible(true);
        grid.addItemDoubleClickListener(e -> {
            comboBox.setValue(e.getItem().科目名());
            grid.setVisible(false);
        });
        add(new H3("学年"), schoolYearsRadioButton, new H3("学部・学科"), departmentsRadioButton);
        add(new H3("必選別"), subjectTypesRadioButton, new H3("成績評価"), gradeRadioButton, grid);
    }

    private String changeDepartmentValue(String department) {
        if(department.equals("理工学部 情報ｼｽﾃﾑ工学科")) return "情報システム工学科";
        return department;
    }

    private class Filter implements SerializablePredicate<StudentTable> {
        @Override
        public boolean test(StudentTable studentTable) {
            boolean schoolYear = false, department = false, subjectType = false, grade = false;
            if(schoolYearsRadioButton.getValue().equals("全体") || (studentTable.対象学年() + "年生").equals(schoolYearsRadioButton.getValue())) schoolYear = true;
            if(departmentsRadioButton.getValue().equals("全体") || changeDepartmentValue(studentTable.対象学科()).equals(departmentsRadioButton.getValue())) department = true;
            if(subjectTypesRadioButton.getValue().equals("全体") || studentTable.必選別().equals(subjectTypesRadioButton.getValue())) subjectType = true;
            if(gradeRadioButton.getValue().equals("全体") || studentTable.成績評価().equals(gradeRadioButton.getValue())) grade = true;
            return schoolYear && department && subjectType && grade;
        }
    }
}
