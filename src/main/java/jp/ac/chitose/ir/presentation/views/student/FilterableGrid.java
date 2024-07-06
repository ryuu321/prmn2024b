package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.function.ValueProvider;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FilterableGrid<FilterType, ItemType> implements FilterableComponent<FilterType, ItemType> {
    private final List<Filter<FilterType, ItemType>> filters = new ArrayList<>();
    private final GridListDataView<ItemType> dataView;
    private final Grid<ItemType> grid;

    public FilterableGrid(Collection<ItemType> items) {
        grid = new Grid<>();
        this.dataView = grid.setItems(items);
    }

    public FilterableGrid(Class<ItemType> beanType, boolean autoCreateColumns) {
        grid = new Grid<>(beanType, autoCreateColumns);
        this.dataView = grid.getListDataView();
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

    public List<Filter<?, ?>> getFilters() {
        return Collections.unmodifiableList(filters);
    }

    @Override
    public Component getComponent() {
        return grid;
    }

    public void setWidthFull() {
        grid.setWidthFull();
    }

    public void setAllRowsVisible(boolean allRowsVisible) {
        grid.setAllRowsVisible(allRowsVisible);
    }

    public Grid.Column<ItemType> addColumn(ValueProvider<ItemType, FilterType> valueProvider) {
        return grid.addColumn(valueProvider);
    }

    public void addItemClickListener(ComponentEventListener<ItemClickEvent<ItemType>> listener) {
        grid.addItemClickListener(listener);
    }

    public GridListDataView<ItemType> setItems(List<ItemType> items) {
        return grid.setItems(items);
    }
}
