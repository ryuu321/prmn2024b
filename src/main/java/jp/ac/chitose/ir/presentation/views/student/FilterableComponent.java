package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.Component;

public interface FilterableComponent<FilterType, ItemType> {
    void filter();
    Component getComponent();
}
