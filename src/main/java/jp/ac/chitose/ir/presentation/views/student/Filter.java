package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.Component;

public interface Filter<FilterType, ItemType> {
    Component getFilterComponent();
    boolean applyFilter(ItemType item);
    void addValueChangeListener(Runnable valueChangeListener);
}
