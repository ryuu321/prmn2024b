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
    public void addUser(String username, boolean[] role, LocalDateTime createAt) throws RuntimeException{
        usersRepository.addUser(username);
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
    public int deleteUser(String userId, String username, LocalDateTime deleteAt){
        // ログイン中のユーザと同じ場合は1を返す
        if(username.equals(securityService.getLoginUser().getUsername())) {
            return 1;
        }

        // userIdが数値に変換できる場合は削除
        // 成功 → 0, 失敗 → 2 を返す
        if(isNumber(userId)){
            long id = Integer.parseInt(userId);
            int deleted = usersRepository.deleteUser(id, username, deleteAt);
            if(deleted == 1) return 0;
            return 2;
        }

        // userIdが数値に変換できない場合は3を返す
        return 3;
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
