package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.Component;

import java.util.function.BiPredicate;

public interface Filter<T, U> {
    Component getFilterComponent();
    void setFilter(BiPredicate<U, T> filter);
    boolean filter(U item);
}
