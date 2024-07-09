package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.ValueProvider;

import java.util.List;


public class FilteredGrid<FilterType, ItemType> extends VerticalLayout implements FilteredComponent<FilterType, ItemType> {
    private final FilterableGrid<FilterType, ItemType> filterableGrid;

    public FilteredGrid(Class<ItemType> beanTypeClass, List<ValueProvider<ItemType, FilterType>> valueProviders, List<String> headerNames, List<Filter<FilterType, ItemType>> filters, FilterPosition filterPosition) {
        filterableGrid = new FilterableGrid<>(beanTypeClass, false);
        filters.forEach(filterableGrid::addFilter);
        filters.forEach(this::registerThis);
        addColumns(valueProviders, headerNames);
        setupLayout(filterPosition);
    }

    public FilteredGrid(FilterableGrid<FilterType, ItemType> filterableGrid, List<Filter<FilterType, ItemType>> filters, FilterPosition filterPosition) {
        this.filterableGrid = filterableGrid;
        filters.forEach(filterableGrid::addFilter);
        filters.forEach(this::registerThis);
        setupLayout(filterPosition);
    }

    @Override
    public void setupLayout(FilterPosition filterPosition) {
        filterPosition.apply(this, filterableGrid.getComponent(), filterableGrid.getFilters());
    }

    @Override
    public void registerThis(Filter<FilterType, ItemType> filter) {
        filter.registerComponent(this);
    }

    public void setAllRowsVisible(boolean allRowsVisible) {
        filterableGrid.setAllRowsVisible(allRowsVisible);
    }

    private void addColumns(List<ValueProvider<ItemType, FilterType>> valueProviders, List<String> headerName) {
        for(int i = 0; i < Math.min(valueProviders.size(), headerName.size()); i++) {
            filterableGrid.addColumn(valueProviders.get(i)).setHeader(headerName.get(i));
        }
    }

    public void addItemClickListener(ComponentEventListener<ItemClickEvent<ItemType>> listener) {
        filterableGrid.addItemClickListener(listener);
    }

    @Override
    public void filter() {
        filterableGrid.filter();
    }

    public void addFilter(Filter<FilterType, ItemType> filter) {
        filterableGrid.addFilter(filter);
        registerThis(filter);
    }

    public void removeFilter(Filter<FilterType, ItemType> filter) {
        filterableGrid.removeFilter(filter);
    }

    public void clearFilters() {
        filterableGrid.clearFilters();
    }

    public void setItems(List<ItemType> items) {
        filterableGrid.setItems(items);
    }
}