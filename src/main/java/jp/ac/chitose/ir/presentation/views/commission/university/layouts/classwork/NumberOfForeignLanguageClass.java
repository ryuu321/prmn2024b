package jp.ac.chitose.ir.presentation.views.commission.university.layouts.classwork;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

//外国語科目数
public class NumberOfForeignLanguageClass extends VerticalLayout {
    public NumberOfForeignLanguageClass() {
                    add(new H1("外国語科目数"));
                    add(new Paragraph("説明"));
    }
}
