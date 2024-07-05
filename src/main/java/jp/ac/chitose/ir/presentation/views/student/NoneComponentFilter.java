package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.Component;

import java.util.function.BiPredicate;

public class NoneComponentFilter<T, U> implements Filter<T, U> {
    private T value;
    private BiPredicate<U, T> filter;

    public NoneComponentFilter(BiPredicate<U, T> filter, T value) {
        this.filter = filter;
        this.value = value;
    }

    @Override
    public Component getFilterComponent() {
        return null;
    }

    @Override
    public void setFilterPredicate(BiPredicate<U, T> filter) {
        this.filter = filter;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public boolean applyFilter(U item) {
        return filter.test(item, value);
    }
}
