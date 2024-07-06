package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.Component;

import java.util.function.BiPredicate;

public interface Filter<FilterType, ItemType> {
    Component getFilterComponent();
    void setFilterPredicate(BiPredicate<ItemType, FilterType> filter);
    boolean applyFilter(ItemType item);
}
