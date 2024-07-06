package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;

import java.util.function.BiPredicate;

public class RadioButtonFilter<FilterType, ItemType> implements Filter<FilterType, ItemType> {
    private final RadioButtonGroup<FilterType> radioButton;
    private BiPredicate<ItemType, FilterType> filter;

    public RadioButtonFilter(final RadioButtonGroup<FilterType> radioButton, BiPredicate<ItemType, FilterType> filter) {
        this.radioButton = radioButton;
        this.filter = filter;
    }

    public RadioButtonFilter(FilterType[] values, BiPredicate<ItemType, FilterType> filter) {
        this.radioButton = createRadioButtonGroup(values);
        this.filter = filter;
    }

    public RadioButtonFilter(final RadioButtonGroup<FilterType> radioButton, BiPredicate<ItemType, FilterType> filter, String idName) {
        this.radioButton = radioButton;
        this.filter = filter;
        radioButton.getElement().setAttribute("id", idName);
    }

    public RadioButtonFilter(FilterType[] values, BiPredicate<ItemType, FilterType> filter, String idName) {
        this.radioButton = createRadioButtonGroup(values);
        this.filter = filter;
        radioButton.getElement().setAttribute("id", idName);
    }

    @Override
    public void registerComponent(FilteredComponent<FilterType, ItemType> filteredComponent) {
        radioButton.addValueChangeListener(event -> filteredComponent.filter());
    }

    private static <T> RadioButtonGroup<T> createRadioButtonGroup(T[] values) {
        RadioButtonGroup<T> radioButtonGroup = new RadioButtonGroup<>();
        radioButtonGroup.setItems(values);
        radioButtonGroup.setValue(values[0]);
        return radioButtonGroup;
    }

    @Override
    public void setFilterPredicate(BiPredicate<ItemType, FilterType> filter) {
        this.filter = filter;
    }

    @Override
    public boolean applyFilter(final ItemType item) {
        return filter.test(item, radioButton.getValue());
    }

    @Override
    public Component getFilterComponent() {
        return radioButton;
    }
}
