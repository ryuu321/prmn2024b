package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FilterableGrid<T, U> extends Grid<U> implements FilteredComponent {
    private final List<Filter<T, U>> filters = new ArrayList<>();
    private final GridListDataView<U> dataView = getListDataView();

    public FilterableGrid(Collection<U> items) {
        super(items);
    }

    public FilterableGrid(Class<U> beanType, boolean autoCreateColumns) {
        super(beanType, autoCreateColumns);
    }

    @Override
    public void filter() {
        dataView.setFilter(item -> filters.stream()
                .map(filter -> filter.applyFilter(item))
                .reduce(true, (isMatch, filterResult) -> isMatch && filterResult));
    }

    public void addFilter(Filter<T, U> filter) {
        filters.add(filter);
    }

    public void removeFilter(Filter<T, U> filter) {
        filters.remove(filter);
    }

    public void clearFilters() {
        filters.clear();
    }

    public List<Filter<T, U>> getFilters() {
        return Collections.unmodifiableList(filters);
    }
}
