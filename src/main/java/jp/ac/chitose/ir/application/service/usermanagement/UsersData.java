package jp.ac.chitose.ir.application.service.usermanagement;

public record UsersDataGrid(
        int id,
        String user_name,
        boolean is_available,
        String display_name
) {

}
