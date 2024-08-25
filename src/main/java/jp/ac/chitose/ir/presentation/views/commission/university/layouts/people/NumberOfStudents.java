package jp.ac.chitose.ir.presentation.views.commission.university.layouts.people;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

//学生数
public class NumberOfStudents extends VerticalLayout {
    public NumberOfStudents() {
        add(new H1("学生数"));
        add(new Paragraph("説明"));
    }
}

