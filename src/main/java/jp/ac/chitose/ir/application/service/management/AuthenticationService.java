package jp.ac.chitose.ir.application.service.management;

import jp.ac.chitose.ir.infrastructure.repository.AuthenticationRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final AuthenticationRepository authenticationRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(AuthenticationRepository authenticationRepository, PasswordEncoder passwordEncoder) {
        this.authenticationRepository = authenticationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> authenticate(String loginId, String password) {

        // DBと接続して情報を取ってくる処理。AuthenticationRepositoryで実際の処理をかく
        // todo データベースの移行が完了したら以下の分岐処理は削除する
        Optional<User> userOp = authenticationRepository.getUserInformation(loginId, password);
        if(userOp.isPresent()){
            System.out.println("平文で認証します");
            System.out.println("認証処理");
            return userOp;
        }

        // loginIdからパスワードを取得
        Optional<Password> passwordOp = authenticationRepository.getPassword(loginId);

        // そもそもloginIdが存在しなければ空のリストを返す
        if(passwordOp.isEmpty()){
            System.out.println("ログインIDが存在しません");
            return userOp;
        }

        // ハッシュ化したパスワードが一致していればユーザ情報を取得
        // todo 全部がハッシュ化に対応したらtry-catchは消しても良いかも
        try {
            if (passwordEncoder.matches(password, passwordOp.get().value())) {
                System.out.println("平文で存在しなかったのでハッシュ化したパスワードで認証します");
                userOp = authenticationRepository.getUserInformation(loginId);
            }
        } catch (IllegalArgumentException ignore){

        }

        System.out.println("認証処理");
        return userOp;
    }
}
