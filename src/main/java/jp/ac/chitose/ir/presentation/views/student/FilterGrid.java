package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import jp.ac.chitose.ir.application.service.student.StudentGrade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FilterGrid<T, U> extends Grid<U> implements FilteredComponent {
    private final List<Filter<T, U>> filters = new ArrayList<>();
    private final GridListDataView<U> dataView = getListDataView();

    public FilterGrid(Collection<U> items) {super(items);}

    public FilterGrid(Class<U> beanType, boolean autoCreateColumns) {
        super(beanType, autoCreateColumns);
    }

    @Override
    public void filter() {
        dataView.setFilter(item -> filters.stream()
                        .map(filter -> filter.filter(item))
                        .reduce(true, (isMatch, filterResult) -> isMatch && filterResult));
    }

    public void addFilter(Filter<T, U> filter) {
        filters.add(filter);
    }

    public void removeFilter(Filter<T, U> filter) {
        filters.remove(filter);
    }

    public List<Filter<T, U>> getFilters() {
        return filters;
    }
}
