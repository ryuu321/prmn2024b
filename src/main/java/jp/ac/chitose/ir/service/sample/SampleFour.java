package jp.ac.chitose.ir.service.sample;

import java.util.List;

public record SampleFour(
        List<SampleFour.Row> data
) {
    public record Row(
            String year
    ){}
}


