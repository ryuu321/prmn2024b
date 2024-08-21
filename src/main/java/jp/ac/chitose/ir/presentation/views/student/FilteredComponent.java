package jp.ac.chitose.ir.presentation.views.student;

public interface FilteredComponent<FilterType, ItemType> {
    void filter();
    void setupLayout(FilterPosition filterPosition);
    void registerThis(Filter<FilterType, ItemType> filter);
}
