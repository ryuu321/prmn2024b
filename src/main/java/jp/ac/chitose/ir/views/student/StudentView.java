package jp.ac.chitose.ir.views.student;

import com.github.appreciated.apexcharts.ApexCharts;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.hierarchy.TreeData;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jp.ac.chitose.ir.service.student.StudentGrade;
import jp.ac.chitose.ir.service.student.StudentService;
import jp.ac.chitose.ir.service.student.StudentTable;
import jp.ac.chitose.ir.views.MainLayout;

import java.text.Normalizer;
import java.util.function.Consumer;

@PageTitle("GradeStudent")
@Route(value = "grade/student", layout = MainLayout.class)
public class StudentView extends VerticalLayout {
    private final StudentService studentService;
    private ComboBox<String> comboBox;
    private TextField textField;
    private VerticalLayout chart;
    private RadioButtonGroup<String> schoolYearsRadioButton;
    private RadioButtonGroup<String> departmentsRadioButton;
    private Grid<StudentTable> grid;
    private GraphPattern graphPattern;

    public StudentView(StudentService studentService) {
        this.studentService = studentService;
        setup();
        System.out.println(textField.getParent());
    }

    private void setup() {
        graphPattern = new GPAGraphPattern();
        textFieldInitialize();
        schoolYearsRadioButtonInitialize();
        departmentsRadioButtonInitialize();
        comboBoxInitialize();
        grid = new Grid<>(StudentTable.class, false);

        add(textField, new H1("Student"), new Paragraph("説明"));
        add(new H3("学年"), schoolYearsRadioButton, new H3("学科"), departmentsRadioButton, comboBox);
        addChart(schoolYearsRadioButton.getValue(), departmentsRadioButton.getValue());
    }

    private void textFieldInitialize() {
        textField = new TextField();
        textField.addValueChangeListener(valueChangeEvent -> {
            if(valueChangeEvent.getValue() == null || valueChangeEvent.getValue().isEmpty()) return;
            comboBox.setItems(studentService.getStudentNumberGrades(valueChangeEvent.getValue()).data().stream()
                    .map(StudentGrade::科目名).toList());
            Grid.Column<StudentTable> departmentColumn = grid.addColumn(StudentTable::科目名);
            Grid.Column<StudentTable> schoolYearColumn = grid.addColumn(data -> data.対象学年() + "年生");
            Grid.Column<StudentTable> subjectTypeColumn = grid.addColumn(StudentTable::必選別);
            Grid.Column<StudentTable> gradeColumn = grid.addColumn(StudentTable::成績評価);
            GridListDataView<StudentTable> gridListDataView = grid.setItems(studentService.getStudentTable(valueChangeEvent.getValue()).data());
            StudentTableFilter filter = new StudentTableFilter(gridListDataView);
            grid.setWidthFull();
            grid.setAllRowsVisible(true);
            grid.addItemDoubleClickListener(e -> {
                comboBox.setValue(e.getItem().科目名());
                grid.setVisible(false);
            });
            grid.getHeaderRows().clear();
            HeaderRow headerRow = grid.appendHeaderRow();
            headerRow.getCell(departmentColumn).setComponent(createFilterHeader("科目名", filter::setDepartment));
            headerRow.getCell(schoolYearColumn).setComponent(createFilterHeader("学年", filter::setSchoolYear));
            headerRow.getCell(subjectTypeColumn).setComponent(createFilterHeader("必選別", filter::setSubjectType));
            headerRow.getCell(gradeColumn).setComponent(createFilterHeader("成績評価", filter::setGrade));
            add(grid);
        });
    }

    private void schoolYearsRadioButtonInitialize() {
        schoolYearsRadioButton = new RadioButtonGroup<>("", "全体", "1年生", "2年生", "3年生", "4年生", "修士1年生", "修士2年生");
        schoolYearsRadioButton.setValue("全体");
        schoolYearsRadioButton.addValueChangeListener(valueChangeEvent -> {
            if(!valueChangeEvent.getValue().equals(valueChangeEvent.getOldValue())) {
                updateChart(valueChangeEvent.getValue(), departmentsRadioButton.getValue());
            }
        });
        add(schoolYearsRadioButton);
    }

    private void departmentsRadioButtonInitialize() {
        departmentsRadioButton = new RadioButtonGroup<>("", "全体", "応用科学生物学科", "電子光工学科", "情報システム工学科", "理工学研究科");
        departmentsRadioButton.setValue("全体");
        departmentsRadioButton.addValueChangeListener(valueChangeEvent -> {
            if(!valueChangeEvent.getValue().equals(valueChangeEvent.getOldValue())) {
                updateChart(schoolYearsRadioButton.getValue(), valueChangeEvent.getValue());
            }
        });
    }

    private void comboBoxInitialize() {
        comboBox = new ComboBox<>("科目名");
        comboBox.setWidth("600px");
        comboBox.setPlaceholder("GPAのグラフを表示しています。選んだ科目のグラフに切り替わります。");
        comboBox.setClearButtonVisible(true);
        comboBox.addValueChangeListener(valueChangeEvent -> {
            if(valueChangeEvent.getValue() == null) {
                graphPattern = new GPAGraphPattern();
                if(textField.getValue() != null) {
                    comboBox.clear();
                    comboBox.setItems(studentService.getStudentNumberGrades(textField.getValue()).data().stream()
                            .map(StudentGrade::科目名).toList());
                }
                grid.setVisible(true);
            } else {
                graphPattern = new SubjectGraphPattern(studentService.getStudentNumberGrade(textField.getValue(), valueChangeEvent.getValue()).data().get(0));
                grid.setVisible(false);
            }
            updateChart(schoolYearsRadioButton.getValue(), departmentsRadioButton.getValue());
        });
    }

    private void updateChart(String schoolYear, String department) {
        if(chart != null) remove(chart, grid);
        addChart(schoolYear, department);
    }

    private void addChart(String schoolYear, String department) {
        chart = graphPattern.create(studentService, schoolYear, department);
        add(chart, grid);
    }

    private static Component createFilterHeader(String labelText, Consumer<String> filterChangeConsumer) {
        NativeLabel label = new NativeLabel(labelText);
        label.getStyle().set("padding-top", "var(--lumo-space-m)")
                .set("font-size", "var(--lumo-font-size-xs)");
        TextField textField = null;
        ComboBox<String> stringComboBox = null;
        if("科目名".equals(labelText)) {
            textField = new TextField();
            textField.setValueChangeMode(ValueChangeMode.EAGER);
            textField.setClearButtonVisible(true);
            textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
            textField.setWidthFull();
            textField.getStyle().set("max-width", "100%");
            textField.addValueChangeListener(
                    e -> filterChangeConsumer.accept(e.getValue()));
        } else {
            stringComboBox = new ComboBox<>();
            if("学年".equals(labelText)) {
                stringComboBox.setItems("1年生", "2年生", "3年生", "4年生", "修士1年生", "修士2年生");
            } else if("必選別".equals(labelText)) {
                stringComboBox.setItems("必修", "選択必修", "選択");
            } else {
                stringComboBox.setItems("不可", "可", "良", "優", "秀");
            }
            stringComboBox.setWidthFull();
            stringComboBox.setClearButtonVisible(true);
            stringComboBox.addValueChangeListener(e -> filterChangeConsumer.accept(e.getValue()));
        }
        VerticalLayout layout = new VerticalLayout(label, (textField == null ? stringComboBox : textField));
        layout.getThemeList().clear();
        layout.getThemeList().add("spacing-xs");
        return layout;
    }

    private static class StudentTableFilter {
        private final GridListDataView<StudentTable> gridListDataView;
        private String department;
        private String schoolYear;
        private String subjectType;
        private String grade;

        public StudentTableFilter(GridListDataView<StudentTable> gridListDataView) {
            this.gridListDataView = gridListDataView;
            this.gridListDataView.addFilter(this::filter);
        }

        public void setDepartment(String department) {
            this.department = department;
            gridListDataView.refreshAll();
        }

        public void setSchoolYear(String schoolYear) {
            this.schoolYear = schoolYear;
            gridListDataView.refreshAll();
        }

        public void setSubjectType(String subjectType) {
            this.subjectType = subjectType;
            gridListDataView.refreshAll();
        }

        public void setGrade(String grade) {
            this.grade = grade;
            gridListDataView.refreshAll();
        }

        private boolean filter(StudentTable studentTable) {
            boolean matchesDepartment = matches(studentTable.科目名(), department);
            boolean matchesSchoolYear = matches(studentTable.対象学年() + "年生", schoolYear);
            boolean matchesSubjectType = matches(studentTable.必選別(), subjectType);
            boolean matchesGrade = matches(studentTable.成績評価(), grade);
            return matchesDepartment && matchesSchoolYear && matchesSubjectType && matchesGrade;
        }

        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty() || normalizeString(value).contains(normalizeString(searchTerm));
        }

        private String normalizeString(String input) {
            return Normalizer.normalize(input, Normalizer.Form.NFKC).toLowerCase().replaceAll("\\s+", " ").trim();
        }
    }
}