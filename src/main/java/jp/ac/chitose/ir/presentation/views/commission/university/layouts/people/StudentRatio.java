package jp.ac.chitose.ir.presentation.views.commission.university.layouts.people;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class StudentRatio extends VerticalLayout {
    public StudentRatio() {
        add(new H1("学部生と大学院生の比率"));
        add(new Paragraph("説明"));
    }
}
