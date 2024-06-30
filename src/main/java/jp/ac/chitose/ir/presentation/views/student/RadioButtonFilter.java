package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;

import java.util.function.BiPredicate;

public class RadioButtonFilter<T, U> implements Filter<T, U> {
    final private RadioButtonGroup<T> radioButton;
    private BiPredicate<U, T> filter;

    private RadioButtonFilter(final RadioButtonGroup<T> radioButton) {
        this.radioButton = radioButton;
    }

    public static <T, U> RadioButtonFilter<T, U> createRadioButtonFilter(FilteredComponent component, T[] values, BiPredicate<U, T> filterPredicate) {
        RadioButtonGroup<T> radioButtonGroup = new RadioButtonGroup<>();
        radioButtonGroup.setItems(values);
        radioButtonGroup.setValue(values[0]);

        RadioButtonFilter<T, U> filter = new RadioButtonFilter<>(radioButtonGroup);
        filter.setFilter(filterPredicate);
        radioButtonGroup.addValueChangeListener(event -> component.filter());

        return filter;
    }

    @Override
    public void setFilter(BiPredicate<U, T> filter) {
        this.filter = filter;
    }

    @Override
    public boolean filter(final U item) {
        return filter.test(item, radioButton.getValue());
    }

    @Override
    public Component getFilterComponent() {
        return radioButton;
    }
}