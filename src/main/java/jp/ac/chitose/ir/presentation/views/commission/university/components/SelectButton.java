package jp.ac.chitose.ir.presentation.views.commission.university.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class SelectButton extends Button {
    public SelectButton(String str, VerticalLayout layout, VerticalLayout mainLayout, BackButton backButton) {
        setText(str);

        setHeight("100px");

        addClickListener(e -> {
            layout.setVisible(true);
            mainLayout.setVisible(false);
            backButton.setVisible(true);
        });
    }

}
