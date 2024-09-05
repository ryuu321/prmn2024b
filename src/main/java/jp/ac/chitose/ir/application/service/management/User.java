package jp.ac.chitose.ir.application.service.management;

public record User(
        long id,
        String login_id,
        String name,
        String password,
        boolean is_available,
        String role
) {
}
