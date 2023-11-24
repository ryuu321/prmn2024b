package jp.ac.chitose.ir.service;

import java.util.List;

public record SampleOne(
        List<Row> data
) {
    public record Row(
            String month,
            long score
    )
    {}
}
