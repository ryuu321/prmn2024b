package jp.ac.chitose.ir.presentation.views.commission.university.layouts.exam;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

//入学定員
public class EnrollmentCapacity extends VerticalLayout {
    public EnrollmentCapacity() {
                    add(new H1("入学定員"));
                    add(new Paragraph("説明"));
    }
}
