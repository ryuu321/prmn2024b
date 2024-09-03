package jp.ac.chitose.ir.presentation.views.student;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.List;

public enum FilterPosition {
    TOP((layout, component, filters) -> {
        filters.stream()
                .filter(filter -> !(filter instanceof NoneComponentFilter))
                .forEach(filter -> {
                    String name = filter.getFilterComponent().getElement().getAttribute("id");
                    if(name != null && !name.isEmpty()) layout.add(new H3(name));
                    layout.add(filter.getFilterComponent());
                });
        layout.add(component);
    }),
    BOTTOM((layout, component, filters) -> {
        layout.add(component);
        filters.stream()
                .filter(filter -> !(filter instanceof NoneComponentFilter))
                .forEach(filter -> {
                    String name = filter.getFilterComponent().getElement().getAttribute("id");
                    if(name != null && !name.isEmpty()) layout.add(new H3(name));
                    layout.add(filter.getFilterComponent());
                });
    }),
    RIGHT((layout, component, filters) -> {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(component);
        filters.stream()
                .filter(filter -> !(filter instanceof NoneComponentFilter))
                .forEach(filter -> {
                    String name = filter.getFilterComponent().getElement().getAttribute("id");
                    if(name != null && !name.isEmpty()) layout.add(new H3(name));
                    horizontalLayout.add(filter.getFilterComponent());
                });
        layout.add(horizontalLayout);
    }),
    LEFT((layout, component, filters) -> {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        filters.stream()
                .filter(filter -> !(filter instanceof NoneComponentFilter))
                .forEach(filter -> {
                    String name = filter.getFilterComponent().getElement().getAttribute("id");
                    if(name != null && !name.isEmpty()) layout.add(new H3(name));
                    horizontalLayout.add(filter.getFilterComponent());
                });
        horizontalLayout.add(component);
        layout.add(horizontalLayout);
    });

    private final TriConsumer<VerticalLayout, Component, List<Filter<?, ?>>> layoutFunction;

    FilterPosition(TriConsumer<VerticalLayout, Component, List<Filter<?, ?>>> layoutFunction) {
        this.layoutFunction = layoutFunction;
    }

    public void apply(VerticalLayout layout, Component component, List<Filter<?, ?>> filters) {
        layoutFunction.accept(layout, component, filters);
    }
}
