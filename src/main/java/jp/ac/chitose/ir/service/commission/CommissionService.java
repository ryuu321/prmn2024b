package jp.ac.chitose.ir.service.commission;

import jp.ac.chitose.ir.service.TableData;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(value = "/commission", accept = "application/json", contentType = "application/json")
public interface CommissionService {
    @GetExchange("/gpa")
    TableData<CommissionGpa> getCommissionGpa();

    @GetExchange("/gpa2")
    TableData<CommissionGpa2>getCommissionGpa2();

    @GetExchange("/gpa/first")
    TableData<CommissionGpaFirst>getCommissionGpaFirst();

    @GetExchange("/gpa2/first")
    TableData<CommissionGpa2First>getCommissionGpa2First();

    @GetExchange("/gpa/second")
    TableData<CommissionGpaSecond>getCommissionGpaSecond();

    @GetExchange("/gpa2/second")
    TableData<CommissionGpa2Second>getCommissionGpa2Second();

    @GetExchange("/gpa/third")
    TableData<CommissionGpaThird>getCommissionGpaThird();

    @GetExchange("/gpa2/third")
    TableData<CommissionGpa2Third>getCommissionGpa2Third();

    @GetExchange("/gpa/fourth")
    TableData<CommissionGpaFourth>getCommissionGpaFourth();

    @GetExchange("/gpa2/fourth")
    TableData<CommissionGpa2Fourth>getCommissionGpa2Fourth();

}
