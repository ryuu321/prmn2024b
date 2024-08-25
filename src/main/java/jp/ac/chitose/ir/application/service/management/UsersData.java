package jp.ac.chitose.ir.application.service.management;

public record UsersData(
        int id,
        String login_id,
        String user_name,
        boolean is_available,
        String display_name
) {

}
