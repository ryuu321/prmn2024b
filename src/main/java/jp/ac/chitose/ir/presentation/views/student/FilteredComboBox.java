package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.Collection;
import java.util.List;

public class FilteredComboBox<FilterType, ItemType> extends VerticalLayout implements FilteredComponent<FilterType, ItemType> {
    private final FilterableComboBox<FilterType, ItemType> filterableComboBox;

    public FilteredComboBox(List<Filter<FilterType, ItemType>> filters, FilterPosition filterPosition) {
        filterableComboBox = new FilterableComboBox<>();
        filters.forEach(filterableComboBox::addFilter);
        filters.forEach(this::registerThis);
        setupLayout(filterPosition);
    }

    public FilteredComboBox(String label, List<Filter<FilterType, ItemType>> filters, FilterPosition filterPosition) {
        filterableComboBox = new FilterableComboBox<>(label);
        filters.forEach(filterableComboBox::addFilter);
        filters.forEach(this::registerThis);
        setupLayout(filterPosition);
    }

    public FilteredComboBox(String label, ItemType[] items, List<Filter<FilterType, ItemType>> filters, FilterPosition filterPosition) {
        filterableComboBox = new FilterableComboBox<>(label, items);
        filters.forEach(filterableComboBox::addFilter);
        filters.forEach(this::registerThis);
        setupLayout(filterPosition);
    }

    public FilteredComboBox(String label, Collection<ItemType> items, List<Filter<FilterType, ItemType>> filters, FilterPosition filterPosition) {
        filterableComboBox = new FilterableComboBox<>(label, items);
        filters.forEach(filterableComboBox::addFilter);
        filters.forEach(this::registerThis);
        setupLayout(filterPosition);
    }

    @Override
    public void filter() {
        filterableComboBox.filter();
    }

    @Override
    public void setupLayout(FilterPosition filterPosition) {
        filterPosition.apply(this, filterableComboBox.getComponent(), filterableComboBox.getFilters());
    }

    @Override
    public void registerThis(Filter<FilterType, ItemType> filter) {
        filter.registerComponent(this);
    }

    public void addFilter(Filter<FilterType, ItemType> filter) {
        filterableComboBox.addFilter(filter);
        registerThis(filter);
    }

    public void removeFilter(Filter<FilterType, ItemType> filter) {
        filterableComboBox.removeFilter(filter);
    }

    public List<Filter<?, ?>> getFilters() {
        return filterableComboBox.getFilters();
    }

    public void setItemLabelGenerator(ItemLabelGenerator<ItemType> itemLabelGenerator) {
        filterableComboBox.setItemLabelGenerator(itemLabelGenerator);
    }

    public void setComboBoxWidth(String width) {
        filterableComboBox.setWidth(width);
    }

    public void setPlaceholder(String placeholder) {
        filterableComboBox.setPlaceholder(placeholder);
    }

    public void setClearButtonVisible(boolean clearButtonVisible) {
        filterableComboBox.setClearButtonVisible(clearButtonVisible);
    }

    public void addValueChangeListener(HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<ComboBox<ItemType>, ItemType>> listener) {
        filterableComboBox.addValueChangeListener(listener);
    }

    public void setItems(List<ItemType> data) {
        filterableComboBox.setItems(data);
    }

    public void setValue(ItemType item) {
        filterableComboBox.setValue(item);
    }
}
