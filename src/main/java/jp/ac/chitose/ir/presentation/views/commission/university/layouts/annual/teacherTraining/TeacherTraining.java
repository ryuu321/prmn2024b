package jp.ac.chitose.ir.presentation.views.commission.university.layouts.annual.teacherTraining;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class TeacherTraining extends VerticalLayout {
    public TeacherTraining() {
        add(new H1("教職課程"));
        add(new H2("・教職課程履修者数"));
        add(new Paragraph("入学年"));
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(new Button("平成22年"));
        buttonLayout.add(new Button("平成23年"));
        add(buttonLayout);
        add(new Heisei22());
    }

}