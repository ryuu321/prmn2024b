package jp.ac.chitose.ir.views.helloworld;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.littemplate.LitTemplate;

import java.util.List;

@Tag("google-chart-wrapper")
@NpmPackage(value="@google-web-components/google-chart", version = "5.0.5")
@JsModule("./src/google-chart-wrapper.ts")
public class GoogleChart extends LitTemplate {

    public GoogleChart(List<Col> cols, List<Row> rows, CHART_TYPE chartType, String options) {
        this.getElement().setPropertyList("cols", cols);
        this.getElement().setPropertyList("rows", rows);
        this.getElement().setProperty("type", chartType.type);
        this.getElement().setProperty("options", options);
        // this.getElement().setProperty("options", "{\"title\": \"Chart Hello!\"}");  // sample
    }

    public GoogleChart(List<Col> cols, List<Row> rows, CHART_TYPE chartType) {
        this.getElement().setPropertyList("cols", cols);
        this.getElement().setPropertyList("rows", rows);
        this.getElement().setProperty("type", chartType.type);
    }

    public GoogleChart(List<Col> cols, List<Row> rows) {
        this.getElement().setPropertyList("cols", cols);
        this.getElement().setPropertyList("rows", rows);
    }

    record Col (String type, String label){}
    record Row (RowValue... c) {}
    record RowValue<T> (T v) {}

    enum CHART_TYPE {
        AREA("area"),
        BAR("bar"),
        MD_BAR("md-bar"),
        BUBBLE("bubble"),
        CALENDAR("calendar"),
        CANDLESTICK("candlestick"),
        COLUMN("column"),
        COMBO("combo"),
        GANTT("gantt"),
        GAUGE("gauge"),
        GEO("geo"),
        HISTOGRAM("histogram"),
        LINE("line"),
        MD_LINE("md-line"),
        ORG("org"),
        PIE("pie"),
        SANKEY("sankey"),
        SCATTER("scatter"),
        STEPPED_AREA("stepped-area"),
        TABLE("table"),
        TIMELINE("timeline"),
        TREEMAP("treemap"),
        WORDTREE("wordtree")
        ;

        String type;
        CHART_TYPE(String type) {
            this.type = type;
        }
    }


}
