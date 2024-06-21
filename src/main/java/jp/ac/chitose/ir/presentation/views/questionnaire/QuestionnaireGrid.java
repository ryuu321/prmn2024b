package jp.ac.chitose.ir.presentation.views.questionnaire;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.function.SerializablePredicate;
import jp.ac.chitose.ir.application.service.questionnaire.QuestionnaireService;
import jp.ac.chitose.ir.application.service.questionnaire.QuestionnaireTopGrid;

public class QuestionnaireGrid extends VerticalLayout {
    private RadioButtonGroup<String> questionnaireRadioButton;
    private RadioButtonGroup<String> schoolYearsRadioButton;
    private RadioButtonGroup<String> departmentsRadioButton;
    private RadioButtonGroup<String> subjectTypesRadioButton;
    private GridListDataView<QuestionnaireTopGrid> gridListDataView;

    // コンストラクタ
    public QuestionnaireGrid(QuestionnaireService questionnaireService) {
        initializeRadioButtons();
        Grid<QuestionnaireTopGrid> grid = initializeGrid(questionnaireService);
        addComponentsToLayout(grid);
    }

    // 各種ラジオボタンの初期化
    private void initializeRadioButtons() {
        initializeQuestionnaireRadioButton();
        initializeSchoolYearsRadioButton();
        initializeDepartmentsRadioButton();
        initializeSubjectTypesRadioButton();
    }

    // アンケート種別選択用ラジオボタンの初期化
    private void initializeQuestionnaireRadioButton() {
        questionnaireRadioButton = new RadioButtonGroup<>();
        questionnaireRadioButton.setItems("IRアンケート", "授業評価アンケート");
        questionnaireRadioButton.setValue("授業評価アンケート");
        questionnaireRadioButton.addValueChangeListener(valueChangeEvent -> applyFilters());
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

    // グリッドの初期化
    private Grid<QuestionnaireTopGrid> initializeGrid(QuestionnaireService questionnaireService) {
        Grid<QuestionnaireTopGrid> grid = new Grid<>(QuestionnaireTopGrid.class, false);
        addColumnsToGrid(grid);
        grid.setWidthFull();
        grid.setAllRowsVisible(true);
        gridListDataView = grid.setItems(questionnaireService.getQuestionnaireTopGrid().data());
        return grid;
    }

    // グリッドにカラムを追加
    private void addColumnsToGrid(Grid<QuestionnaireTopGrid> grid) {
        grid.addColumn(QuestionnaireTopGrid::科目名).setHeader("科目名").setWidth("30%");
        grid.addColumn(data -> data.対象学年() + "年生").setHeader("対象学年");
        grid.addColumn(data -> changeDepartmentValue(data.対象学科())).setHeader("対象学科");
        grid.addColumn(QuestionnaireTopGrid::必選別).setHeader("必選別");
        grid.addColumn(QuestionnaireTopGrid::単位数).setHeader("単位数");
    }

    // コンポーネントをレイアウトに追加
    private void addComponentsToLayout(Grid<QuestionnaireTopGrid> grid) {
        add(new H3("学年"), schoolYearsRadioButton);
        add(new H3("学部・学科"), departmentsRadioButton);
        add(new H3("必選別"), subjectTypesRadioButton);
        add(grid);
    }

    // フィルタを適用
    private void applyFilters() {
        gridListDataView.setFilter(new Filter());
    }

    private String changeDepartmentValue(String department) {
        if ("理工学部 情報ｼｽﾃﾑ工学科".equals(department)) return "情報システム工学科";
        return department;
    }

    // フィルタクラス
    private class Filter implements SerializablePredicate<QuestionnaireTopGrid> {
        @Override
        public boolean test(QuestionnaireTopGrid questionnaireTopGrid) {
            boolean schoolYearMatches = "全体".equals(schoolYearsRadioButton.getValue()) || (questionnaireTopGrid.対象学年() + "年生").equals(schoolYearsRadioButton.getValue());
            boolean departmentMatches = "全体".equals(departmentsRadioButton.getValue()) || changeDepartmentValue(questionnaireTopGrid.対象学科()).equals(departmentsRadioButton.getValue());
            boolean subjectTypeMatches = "全体".equals(subjectTypesRadioButton.getValue()) || questionnaireTopGrid.必選別().equals(subjectTypesRadioButton.getValue());
            return schoolYearMatches && departmentMatches && subjectTypeMatches;
        }
    }

}
