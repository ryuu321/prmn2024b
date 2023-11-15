package jp.ac.chitose.ir.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange(value = "/hello", accept = "application/json", contentType = "application/json")
public interface HelloService {

    @PostExchange
    Hello sayHello(@RequestBody SayHelloRequestBody sayHelloRequestBody);

    record SayHelloRequestBody(
            String message
    ){}
}
