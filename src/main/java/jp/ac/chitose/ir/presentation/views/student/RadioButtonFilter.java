package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;

import java.util.function.BiPredicate;

public class RadioButtonFilter<FilterType, ItemType> implements Filter<FilterType, ItemType> {
    private final RadioButtonGroup<FilterType> radioButton;
    private BiPredicate<ItemType, FilterType> filter;

    private RadioButtonFilter(final RadioButtonGroup<FilterType> radioButton, BiPredicate<ItemType, FilterType> filter) {
        this.radioButton = radioButton;
        this.filter = filter;
    }

    public static <FilterType, ItemType> RadioButtonFilter<FilterType, ItemType> create(FilterableComponent<FilterType, ItemType> component, FilterType[] values, BiPredicate<ItemType, FilterType> filterPredicate) {
        RadioButtonGroup<FilterType> radioButtonGroup = createRadioButtonGroup(values);
        RadioButtonFilter<FilterType, ItemType> filter = new RadioButtonFilter<>(radioButtonGroup, filterPredicate);
        radioButtonGroup.addValueChangeListener(event -> component.filter());

        return filter;
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
