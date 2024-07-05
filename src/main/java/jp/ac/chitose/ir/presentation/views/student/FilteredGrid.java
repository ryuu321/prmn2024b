package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.ValueProvider;

import java.util.List;
import java.util.function.BiPredicate;


public class FilteredGrid<FilterType, ItemType> extends VerticalLayout {
    private final FilterableGrid<FilterType, ItemType> filterableGrid;

    public FilteredGrid(Class<ItemType> beanTypeClass, List<ValueProvider<ItemType, FilterType>> valueProviders, List<String> headerNames) {
        filterableGrid = new FilterableGrid<>(beanTypeClass, false);
        addColumns(valueProviders, headerNames);
        setupLayout();
        add(filterableGrid);
    }

    public FilteredGrid(FilterableGrid<FilterType, ItemType> filterableGrid) {
        this.filterableGrid = filterableGrid;
        setupLayout();
        add(filterableGrid);
    }

    private void setupLayout() {
        filterableGrid.setWidthFull();
        filterableGrid.setAllRowsVisible(true);
    }

    private void addColumns(List<ValueProvider<ItemType, FilterType>> valueProviders, List<String> headerName) {
        for(int i = 0; i < Math.min(valueProviders.size(), headerName.size()); i++) {
            filterableGrid.addColumn(valueProviders.get(i)).setHeader(headerName.get(i));
        }
    }

    public void addItemClickListener(ComponentEventListener<ItemClickEvent<ItemType>> listener) {
        filterableGrid.addItemClickListener(listener);
    }

    public void addRadioButtonFilters(List<RadioButtonValues> filterValues, List<BiPredicate<ItemType, FilterType>> filterFunctions, List<String> filterNames) {
        for (int i = 0; i < Math.min(filterValues.size(), Math.min(filterFunctions.size(), filterNames.size())); i++) {
            addRadioButtonFilter(filterValues.get(i), filterFunctions.get(i), filterNames.get(i));
        }
    }

    public void addRadioButtonFilter(RadioButtonValues filterValue, BiPredicate<ItemType, FilterType> filterFunction, String filterName) {
        remove(filterableGrid);
        filterableGrid.addFilter(RadioButtonFilter.create(filterableGrid, (FilterType[])filterValue.getValues(), filterFunction));
        add(new H3(filterName), filterableGrid.getFilters().get(filterableGrid.getFilters().size() - 1).getFilterComponent());
        add(filterableGrid);
    }

    public void filter() {
        filterableGrid.filter();
    }

    public void addFilter(Filter<FilterType, ItemType> filter) {
        filterableGrid.addFilter(filter);
    }

    public void clearFilters() {
        filterableGrid.clearFilters();
    }

    public void setItems(List<ItemType> items) {
        filterableGrid.setItems(items);
    }
}