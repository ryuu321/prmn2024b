package jp.ac.chitose.ir.presentation.views.commission.university.layouts.people;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

//外国人教員
public class ForeignTeacher extends VerticalLayout {
    public ForeignTeacher() {
        add(new H1("外国人教員"));
        add(new Paragraph("説明"));
    }
}