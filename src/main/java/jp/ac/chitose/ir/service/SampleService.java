package jp.ac.chitose.ir.service;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(value = "/sample", accept = "application/json", contentType = "application/json")
public interface SampleService {

    @GetExchange("/one")
    SampleOnes getSampleOne();
}
