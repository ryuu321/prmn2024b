package jp.ac.chitose.ir.presentation.views.commission.university.components;

import com.vaadin.flow.component.button.Button;
import org.vaadin.lineawesome.LineAwesomeIcon;

public class BackButton extends Button {
    public BackButton() {
        setIcon(LineAwesomeIcon.REPLY_SOLID.create());
        setText("Back");
    }
}
