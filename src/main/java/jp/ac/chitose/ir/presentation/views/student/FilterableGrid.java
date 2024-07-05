package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FilterableGrid<FilterType, ItemType> extends Grid<ItemType> implements FilterableComponent {
    private final List<Filter<FilterType, ItemType>> filters = new ArrayList<>();
    private final GridListDataView<ItemType> dataView;

    public FilterableGrid(Collection<ItemType> items) {
        super();
        this.dataView = setItems(items);
    }

    public FilterableGrid(Class<ItemType> beanType, boolean autoCreateColumns) {
        super(beanType, autoCreateColumns);
        this.dataView = getListDataView();
    }

    @Override
    public void filter() {
        dataView.setFilter(item -> filters.stream()
                .allMatch(filter -> filter.applyFilter(item)));
    }

    public void addFilter(Filter<FilterType, ItemType> filter) {
        filters.add(filter);
    }

    public void removeFilter(Filter<FilterType, ItemType> filter) {
        filters.remove(filter);
    }

    public void clearFilters() {
        filters.clear();
    }

    public List<Filter<FilterType, ItemType>> getFilters() {
        return Collections.unmodifiableList(filters);
    }
}
