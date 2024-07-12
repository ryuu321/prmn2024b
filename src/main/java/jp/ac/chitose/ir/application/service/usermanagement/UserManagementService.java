package jp.ac.chitose.ir.application.service.usermanagement;

import jp.ac.chitose.ir.application.service.TableData;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(value = "/users", accept = "application/json", contentType = "application/json")
public interface UserManagementService {
    @GetExchange("/info")
    TableData<UsersData> getUsersData();
}
