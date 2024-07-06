package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.dataview.ComboBoxListDataView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FilterableComboBox<FilterType, ItemType> extends ComboBox<ItemType> implements FilterableComponent<FilterType, ItemType> {
    private final List<Filter<FilterType, ItemType>> filters = new ArrayList<>();
    private final ComboBoxListDataView<ItemType> dataView = getListDataView();

    public FilterableComboBox() {
        super();
    }

    public FilterableComboBox(String label) {
        super(label);
    }

    public FilterableComboBox(String label, ItemType[] items) {
        super(label, items);
    }

    public FilterableComboBox(String label, Collection<ItemType> items) {
        super(label, items);
    }

    @Override
    public void filter() {
        dataView.removeFilters();
        filters.forEach(filter -> dataView.addFilter(filter::applyFilter));
    }

    public void addFilter(Filter<FilterType, ItemType> filter) {
        filters.add(filter);
        dataView.addFilter(filter::applyFilter);
    }

    public void removeFilter(Filter<FilterType, ItemType> filter) {
        filters.remove(filter);
        dataView.removeFilters();
        filters.forEach(f -> dataView.addFilter(f::applyFilter));
    }

    public List<Filter<FilterType, ItemType>> getFilters() {
        return Collections.unmodifiableList(filters);
    }
}
