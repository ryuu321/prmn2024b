package jp.ac.chitose.ir.application.service.class_select;

import jp.ac.chitose.ir.application.service.TableData;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(value = "/class_select", accept = "application/json", contentType = "application/json")
public interface ClassSelect {
    @GetExchange("/test")
    TableData<ClassTest> getClassTest();

    @GetExchange("/nvPWq")
    TableData<ClassnvPWq> gerClassnvPWq();

    @GetExchange("/ec3Tr")
    TableData<Classec3Tr> getClassec3Tr();

    @GetExchange("/7hXWV")
    TableData<Class7hXWV> getClass7hXWV();

    @GetExchange("/review/graph/{subject_id}")
    TableData<ClassQPOJFICHKVJB> getClassQPOJFICHKVJB(@PathVariable String subject_id);

    @GetExchange("/review/description/{subject_id}")
    TableData<ReviewQPOJFICHKVJBDescription> getReviewQPOJFICHKVJBDescription(@PathVariable String subject_id);

    @GetExchange("/review/title/{subject_id}")
    TableData<ReviewTitle> getReviewTitle(@PathVariable String subject_id);//質問事項

}

