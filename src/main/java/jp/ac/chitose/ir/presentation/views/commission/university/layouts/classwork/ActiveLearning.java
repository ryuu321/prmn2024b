package jp.ac.chitose.ir.presentation.views.commission.university.layouts.classwork;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

//アクティブラーニング実施率
public class ActiveLearning extends VerticalLayout {
    public ActiveLearning() {
                add(new H1("アクティブラーニング実施率"));
                add(new Paragraph("説明"));
    }
}
