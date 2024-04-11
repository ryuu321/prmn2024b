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

}
