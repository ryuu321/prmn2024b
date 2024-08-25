package jp.ac.chitose.ir.application.service.management;

import jp.ac.chitose.ir.infrastructure.repository.RoleRepository;
import jp.ac.chitose.ir.infrastructure.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsersService {
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final SecurityService securityService;

    public UsersService(UsersRepository usersRepository, RoleRepository roleRepository, SecurityService securityService){
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
        this.securityService = securityService;
    }

    // ユーザ追加(単体)
    // ロールが1つもない場合は例外を起こしてロールバックする(想定)
    // todo ロールの所在確認をViewにお願いする
    public int addUser(String loginId, String username, String password, List<String> roleList){
        // loginidでユーザが取得できるか判断
        // 既に登録されてたら1を返す
        if(usersRepository.getUsersCount(loginId) > 0) return 1;

        // usersテーブルにユーザを追加
        long userId = usersRepository.addUser(loginId, username, password);
        //long userId = usersRepository.getUserId(username);

        // user_roleテーブルに追加
        for(String role : roleList){
            int roleId = roleRepository.getRoleId(role).get();
            usersRepository.addRole(userId, roleId);
        }
        return 0;
    }

    // csvによるユーザ一括追加
    // 途中でおかしくなったらロールバックしてデータが残らないようにする
    public void addUsers(){

    }

    // ユーザ更新
    public void updateUser(){

    }

    // ユーザ削除
    public int deleteUser(long id){
        // ログイン中のユーザと同じ場合は1を返す
        if(id == securityService.getLoginUser().getId()) {
            return 1;
        }

        // 成功(存在していた) → 0, 失敗(存在しなかった) → 2 を返す
        int deleted = usersRepository.deleteUser(id);
        if(deleted == 1) return 0;
        return 2;
    }

    // csvによるユーザ一括削除
    // 途中でおかしくなったらロールバック
    public void deleteUsers(){

    }

}
