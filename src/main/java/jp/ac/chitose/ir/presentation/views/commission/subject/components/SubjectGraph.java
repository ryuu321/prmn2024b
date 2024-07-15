package jp.ac.chitose.ir.presentation.views.commission.subject;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import jp.ac.chitose.ir.application.service.TableData;
import jp.ac.chitose.ir.application.service.commission.CommissionService;
import jp.ac.chitose.ir.application.service.commission.commission_subject.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class SubjectGraph {
    private CommissionService commissionService;
    private TableData<Subject> subjectTableData;

    public SubjectGraph(CommissionService com) {
        this.commissionService = com;
        this.subjectTableData = commissionService.getSubject();

    }


    public VerticalLayout SubjectGrid() {
        Grid<Subject> grid = new Grid<>();

        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addColumn(createSubjectRenderer()).setHeader("開講年").setFlexGrow(0);
        grid.addColumn(Subject::subjects).setHeader("科目名").setWidth("280px");
        //Grid.Column<Subject> subjectColumn =grid.addColumn(Subject::majors);//.setHeader("学科");
        grid.addColumn(Subject::S).setHeader("秀(%)").setSortable(true);
        grid.addColumn(Subject::A).setHeader("優(%)").setSortable(true);
        grid.addColumn(Subject::B).setHeader("良(%)").setSortable(true);
        grid.addColumn(Subject::C).setHeader("可(%)").setSortable(true);
        grid.addColumn(Subject::D).setHeader("不可(%)").setSortable(true);
        grid.addColumn(Subject::E).setHeader("欠席(%)").setSortable(true);

        TableData<Subject> subjects = this.subjectTableData;
        GridListDataView<Subject> dataView = grid.setItems(subjects.data());
        int i=0;
        grid.addSelectionListener(selection -> {
            //List list=new ArrayList<>(selection.getAllSelectedItems());
            List list= new ArrayList<>(selection.getAllSelectedItems());




            System.out.println(selection.getAllSelectedItems());
        });

        TextField searchField = new TextField();
        searchField.setWidth("30%");
        searchField.setPlaceholder("開講年または科目名で検索可能");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> dataView.refreshAll());

        dataView.addFilter(subject -> {
            String searchTerm = searchField.getValue().trim();

            if (searchTerm.isEmpty())
                return true;
            String subject_year = Integer.valueOf(subject.years()).toString();

            boolean matchYear = matchesTerm(subject_year,searchTerm);
            boolean matchSubject = matchesTerm(subject.subjects(),searchTerm);

            return matchYear || matchSubject;
        });
        VerticalLayout layout = new VerticalLayout(searchField,grid);
        layout.setPadding(false);






        return layout;
    }


    private static Renderer<Subject> createSubjectRenderer() {
        return LitRenderer.<Subject> of(
                        "<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                                + "  <span> ${item.years} </span>"
                                + "</vaadin-horizontal-layout>")
                .withProperty("subjects", Subject::subjects)
                .withProperty("years", Subject::years);
    }

    private boolean matchesTerm(String value, String searchTerm) {
        return value.toLowerCase().contains(searchTerm.toLowerCase());
    }


    private void getSelectedItem(Set<Subject> selection){


    }

}

