package jp.ac.chitose.ir.presentation.component.scroll;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ScrollOptions;

public class Scroll {
    private final Component component;
    private final String id;

    public Scroll(Component component, String idName) {
        this.component = component;
        this.id = idName;
    }

    public Component getComponent() {
        return component;
    }

    public String getId() {
        return id;
    }

    public void scroll() {
        component.scrollIntoView();
    }

    public void scroll(ScrollOptions option) {
        component.scrollIntoView(option);
    }

    @Override
    public int hashCode() {
        return 31 * component.hashCode() + id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Scroll scroll)) return false;
        return this.component.equals(scroll.component) && this.id.equals(scroll.id);
    }
}
