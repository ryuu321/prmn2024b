package jp.ac.chitose.ir.service.class_select;

import jp.ac.chitose.ir.service.TableData;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(value = "/class_select", accept = "application/json", contentType = "application/json")
public interface ClassSelect {
    @GetExchange("/test")
    TableData<ClassTest> getClassTest();
}
