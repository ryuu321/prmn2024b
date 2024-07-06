package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.dataview.ComboBoxListDataView;
import jp.ac.chitose.ir.application.service.student.StudentGrade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FilterableComboBox<FilterType, ItemType> implements FilterableComponent<FilterType, ItemType> {
    private final List<Filter<FilterType, ItemType>> filters = new ArrayList<>();
    private final ComboBoxListDataView<ItemType> dataView;
    private final ComboBox<ItemType> comboBox;

    public FilterableComboBox() {
        comboBox = new ComboBox<>();
        dataView = comboBox.getListDataView();
    }

    public FilterableComboBox(String label) {
        comboBox = new ComboBox<>(label);
        dataView = comboBox.getListDataView();
    }

    public FilterableComboBox(String label, ItemType[] items) {
        comboBox = new ComboBox<>(label, items);
        dataView = comboBox.getListDataView();
    }

    public FilterableComboBox(String label, Collection<ItemType> items) {
        comboBox = new ComboBox<>(label, items);
        dataView = comboBox.getListDataView();
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

    @Override
    public Component getComponent() {
        return comboBox;
    }

    public void setItemLabelGenerator(ItemLabelGenerator<ItemType> itemLabelGenerator) {
        comboBox.setItemLabelGenerator(itemLabelGenerator);
    }

    public void setWidth(String width) {
        comboBox.setWidth(width);
    }

    public void setPlaceholder(String placeholder) {
        comboBox.setPlaceholder(placeholder);
    }

    public void setClearButtonVisible(boolean clearButtonVisible) {
        comboBox.setClearButtonVisible(clearButtonVisible);
    }

    public void addValueChangeListener(HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<ComboBox<ItemType>, ItemType>> listener) {
        comboBox.addValueChangeListener(listener);
    }

    public void setItems(List<ItemType> data) {
        comboBox.setItems(data);
    }

    public void setValue(ItemType item) {
        comboBox.setValue(item);
    }
}
