package jp.ac.chitose.ir.application.service.management;

import jp.ac.chitose.ir.infrastructure.repository.AuthenticationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private AuthenticationRepository authenticationRepository;

    public AuthenticationService(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    public Optional<User> authenticate(String username, String password) {
        // DBと接続して情報を取ってくる処理。AuthenticationRepositoryで実際の処理をかく

        Optional<User> userOptional = authenticationRepository.getUserInformation(username, password);
        System.out.println("認証処理");

        return userOptional;
    }
}
