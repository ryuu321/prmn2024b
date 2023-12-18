package jp.ac.chitose.ir.service;

import jp.ac.chitose.ir.service.sample.SampleOne;

import java.util.List;

public record TableData<T>(
        List<T> data

) {
}
