package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.Component;

import java.util.function.BiPredicate;

public interface Filter<T, U> {
    Component getFilterComponent();
    void setFilterPredicate(BiPredicate<U, T> filter);
    boolean applyFilter(U item);
}
