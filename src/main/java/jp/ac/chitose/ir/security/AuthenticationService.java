package jp.ac.chitose.ir.security;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    public Optional<User> authenticate(String username, String password) {
        // DBと接続して情報を取ってくる処理。AuthenticationRepositoryで実際の処理をかく
        System.out.println("認証処理");

        return Optional.empty();
    }
}
