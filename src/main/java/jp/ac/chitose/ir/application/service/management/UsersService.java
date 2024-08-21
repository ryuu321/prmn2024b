package jp.ac.chitose.ir.application.service.management;

import jp.ac.chitose.ir.infrastructure.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final SecurityService securityService;

    public UsersService(UsersRepository usersRepository, SecurityService securityService){
        this.usersRepository = usersRepository;
        this.securityService = securityService;
    }

    // ユーザ追加(単体)
    // ロールが1つもない場合は例外を起こしてロールバックする(想定)
    @Transactional
    public void addUser(String loginId, String username, boolean[] role, LocalDateTime createAt) throws RuntimeException{
        usersRepository.addUser(loginId, username);
        long userId = usersRepository.getUserId(username);
        boolean hasRole = false;
        for(int i=0;i<role.length;i++){
            if(role[i]){
                usersRepository.addRole(userId, i+1);
                hasRole = true;
            }
        }
        if(!hasRole) throw new RuntimeException();
    }

    // csvによるユーザ一括追加
    // 途中でおかしくなったらロールバックしてデータが残らないようにする
    @Transactional
    public void addUsers(){

    }

    // ユーザ更新
    @Transactional
    public void updateUser(){

    }

    // ユーザ削除
    // アノテーションは要らないと思うけど念のためつけた
    @Transactional
    public int deleteUser(String loginId, String username, LocalDateTime deleteAt){
        // ログイン中のユーザと同じ場合は1を返す
        if(username.equals(securityService.getLoginUser().getUsername())) {
            return 1;
        }

        // 成功 → 0, 失敗 → 2 を返す
        int deleted = usersRepository.deleteUser(loginId, username, deleteAt);
        if(deleted == 1) return 0;
        return 2;
    }

    // csvによるユーザ一括削除
    // 途中でおかしくなったらロールバック
    @Transactional
    public void deleteUsers(){

    }

    // 文字列が自然数になっているか判定
    private boolean isNumber(String s){
        return Pattern.compile("^[1-9]?[0-9]*$").matcher(s).find();
    }
}
