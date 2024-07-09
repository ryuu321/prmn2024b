package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.Component;

import java.util.function.BiPredicate;

public class NoneComponentFilter<FilterType, ItemType> implements Filter<FilterType, ItemType> {
    private FilterType value;
    private BiPredicate<ItemType, FilterType> filter;

    public NoneComponentFilter(BiPredicate<ItemType, FilterType> filter, FilterType value) {
        this.filter = filter;
        this.value = value;
    }

    @Override
    public Component getFilterComponent() {
        return null;
    }

    @Override
    public void setFilterPredicate(BiPredicate<ItemType, FilterType> filter) {
        this.filter = filter;
    }

    public void setValue(FilterType value) {
        this.value = value;
    }

    @Override
    public boolean applyFilter(ItemType item) {
        return filter.test(item, value);
    }

    @Override
    public void registerComponent(FilteredComponent<FilterType, ItemType> filteredComponent) {
        return;
    }
}
