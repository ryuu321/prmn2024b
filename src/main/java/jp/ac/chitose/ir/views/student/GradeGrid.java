package jp.ac.chitose.ir.views.student;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.function.SerializablePredicate;
import jp.ac.chitose.ir.service.student.StudentService;
import jp.ac.chitose.ir.service.student.StudentTable;

public class GradeGrid extends VerticalLayout {
    private final RadioButtonGroup<String> schoolYearsRadioButton;
    private final RadioButtonGroup<String> departmentsRadioButton;
    private final RadioButtonGroup<String> subjectTypesRadioButton;
    private final RadioButtonGroup<String> gradeRadioButton;
    private GridListDataView<StudentTable> gridListDataView;
    public GradeGrid(StudentService studentService, ComboBox comboBox, String textFieldValue) {
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
        gradeRadioButton.addValueChangeListener(valueChangeEvent -> gridListDataView.notifyAll());
        Grid<StudentTable> grid = new Grid<>(StudentTable.class, false);
        grid.addColumn(StudentTable::科目名).setHeader("科目名").setWidth("30%");
        grid.addColumn(data -> data.対象学年() + "年生").setHeader("対象学年");
        grid.addColumn(data -> changeDepartmentValue(data.対象学科())).setHeader("対象学科");
        grid.addColumn(StudentTable::必選別).setHeader("必選別");
        grid.addColumn(StudentTable::科目の単位数).setHeader("単位数");
        grid.addColumn(StudentTable::成績評価).setHeader("成績評価");
        gridListDataView = grid.setItems(studentService.getStudentTable(textFieldValue).data());
        grid.setWidthFull();
        grid.setAllRowsVisible(true);
        grid.addItemDoubleClickListener(e -> comboBox.setValue(e.getItem().科目名()));
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
