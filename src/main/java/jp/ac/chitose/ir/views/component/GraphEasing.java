package jp.ac.chitose.ir.views.component;

import com.github.appreciated.apexcharts.config.chart.animations.Easing;

public enum GraphEasing {
    EASEIN(Easing.EASEIN),
    EASEOUT(Easing.EASEOUT),
    LINEAR(Easing.LINEAR),
    EASEINOUT(Easing.EASEINOUT);

    Easing easing;
    GraphEasing(Easing type) {this.easing = type;}
}
