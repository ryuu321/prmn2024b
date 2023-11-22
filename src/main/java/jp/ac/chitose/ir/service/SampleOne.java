package jp.ac.chitose.ir.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SampleOne (
        String month,
        long score
)
{}
