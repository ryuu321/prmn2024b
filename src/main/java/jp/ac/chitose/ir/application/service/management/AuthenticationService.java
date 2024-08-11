package jp.ac.chitose.ir.application.service.management;

import jp.ac.chitose.ir.infrastructure.repository.AuthenticationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
    private AuthenticationRepository authenticationRepository;

    public AuthenticationService(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    public List<User> authenticate(String loginId, String password) {
        // DBと接続して情報を取ってくる処理。AuthenticationRepositoryで実際の処理をかく

        List<User> userList = authenticationRepository.getUserInformation(loginId, password);
        System.out.println("認証処理");

        return userList;
    }
}
