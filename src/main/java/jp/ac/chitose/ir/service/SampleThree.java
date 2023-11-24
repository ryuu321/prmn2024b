package jp.ac.chitose.ir.service;

import java.util.List;

public record SampleThree(
        List<Row> data
) {
    public record Row(
        String 年度,
        double 難易度,
        double 学習量,
        double 進行速度,
        double 興味関心の向上
    ) {}
}
