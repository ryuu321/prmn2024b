package jp.ac.chitose.ir.service.sample;

import java.util.List;

public record SampleTwo(
        List<Row> data
) {
    public record Row(
            String 年度,
            double 秀,
            double 優,
            double 良,
            double 可,
            double 不可,
            double 欠席
    ){}
}
