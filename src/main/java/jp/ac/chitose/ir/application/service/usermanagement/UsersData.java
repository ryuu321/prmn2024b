package jp.ac.chitose.ir.application.service.usermanagement;

public record UsersData(
        int id,
        String user_name,
        boolean is_available,
        String display_name
) {

}
