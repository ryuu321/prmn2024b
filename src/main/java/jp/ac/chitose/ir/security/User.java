package jp.ac.chitose.ir.security;

public record User(
        long id,
        String name,
        String password,
        boolean is_available,
        String role
) {
}
