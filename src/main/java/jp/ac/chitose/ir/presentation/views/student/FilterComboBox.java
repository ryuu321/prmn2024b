package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.dataview.ComboBoxListDataView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FilterComboBox<T, U> extends ComboBox<U> implements FilteredComponent {
    private final List<Filter<T, U>> filters = new ArrayList<>();
    private final ComboBoxListDataView<U> dataView = getListDataView();

    public FilterComboBox() {
        super();
    }

    public FilterComboBox(String label) {
        super(label);
    }

    public FilterComboBox(String label, U[] items) {
        super(label, items);
    }

    public FilterComboBox(String label, Collection<U> items) {
        super(label, items);
    }

    @Override
    public void filter() {
        dataView.removeFilters();
        filters.forEach(f -> dataView.addFilter(f::applyFilter));
    }

    public void addFilter(Filter<T, U> filter) {
        filters.add(filter);
        dataView.addFilter(filter::applyFilter);
    }

    public void removeFilter(Filter<T, U> filter) {
        filters.remove(filter);
        dataView.removeFilters();
        filters.forEach(f -> dataView.addFilter(f::applyFilter));
    }

    public List<Filter<T, U>> getFilters() {
        return Collections.unmodifiableList(filters);
    }
}
