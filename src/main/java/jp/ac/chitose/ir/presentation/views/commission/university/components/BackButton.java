package jp.ac.chitose.ir.presentation.views.commission.university.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;

public class BackButton extends Button {
    public BackButton(ArrayList<VerticalLayout> layouts,VerticalLayout mainLayout) {
        setText("戻る");
        addClickListener(e -> {
            for (VerticalLayout layout : layouts) {
                layout.setVisible(false);
            }
            mainLayout.setVisible(true);
//            backButton.setVisible(false);
        });
    }
}
