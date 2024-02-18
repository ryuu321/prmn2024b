package jp.ac.chitose.ir.views.component;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.helper.Coordinate;
import com.github.appreciated.apexcharts.helper.Series;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.littemplate.LitTemplate;

@Tag("apex-chart-wrapper")
@NpmPackage(value = "apexcharts", version = "3.35.0")
@JsModule("./com/github/appreciated/apexcharts/apexcharts-wrapper.ts")
public class ApexChart extends LitTemplate {
    public ApexChart(Series<Coordinate<String, Double>> series, String chatType) {
        this.getElement().setProperty("a", chatType);
    }
}
