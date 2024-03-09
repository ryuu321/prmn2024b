package jp.ac.chitose.ir.views.component;

public class GraphSeries<T> {
    private String name;
    private T[] data;

    public GraphSeries(T... data) {
        this.data = data;
    }

    public GraphSeries(String name, T... data) {
        this.name = name;
        this.data = data;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setData(T... data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public T[] getData() {
        return data;
    }
}
