package jp.ac.chitose.ir.application.service.management;

import jp.ac.chitose.ir.application.exception.UserManagementException;
import jp.ac.chitose.ir.infrastructure.repository.RoleRepository;
import jp.ac.chitose.ir.infrastructure.repository.UsersRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(rollbackFor = Exception.class)
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
    // todo ロールの所在確認をViewにお願いする？
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
    public long addUsers(InputStream inputStream) throws UserManagementException{
        //csvの読み込み
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            //todo csvが空の場合のエラー処理（flag==0とかで管理）
            boolean isEmpty = true;
            long rowNumber = 0;

            while ((line = reader.readLine()) != null) {
                rowNumber++;
                isEmpty = false;
                String[] data = line.split(",");

                // データが不足している場合
                if(data.length < 4) {
                    throw new UserManagementException(rowNumber + " 行目のデータが不足しています");
                }
                // csvのユーザ情報を取得
                String login_id = data[0];
                String userName = data[1];
                String password = data[2];

                // ユーザ情報を追加
                try {
                    long userId = usersRepository.addUser(login_id, userName, password);
                    // ロールを追加する
                    for(int i=3;i<data.length;i++){
                        Optional<Integer> roleIdOp = roleRepository.getRoleId(data[i]);
                        if(roleIdOp.isEmpty()) throw new UserManagementException(rowNumber + " 行目: " + data[i] + " というロールは存在しません");

                        usersRepository.addRole(userId, roleIdOp.get());
                    }
                } catch (UserManagementException e){
                    throw e;
                } catch (DuplicateKeyException e){
                    e.printStackTrace();
                    throw new UserManagementException(rowNumber + " 行目のユーザのログインIDは既に存在します");
                } catch (RuntimeException e){
                    e.printStackTrace();
                    throw new UserManagementException(rowNumber + " 行目の処理中にエラーが発生しました");
                }
            }
            if(isEmpty) throw new UserManagementException("csvファイルが空です");
            return rowNumber;
        } catch (IOException e) {
            e.printStackTrace();
            throw new UserManagementException("csvファイルが正しく読み込めませんでした");
        }
    }

    // ユーザ更新
    public void updateUser(){

    }

    // ユーザ削除
    // 途中でおかしくなったら例外を投げてロールバック
    // todo 以下の事項を確認する
    //  ・例外の投げ方はこれで良いか
    //  ・呼び出す前にログイン中のユーザが含まれるか確認すべきか
    public void deleteUsers(Set<UsersData> selectedUsers) throws UserManagementException{
        // 1件ずつユーザー情報を取り出して操作する
        for (UsersData user : selectedUsers) {
            long id = user.id();
            if(id == securityService.getLoginUser().getId()) {
                throw new UserManagementException("現在ログイン中のユーザが含まれています");
            }

            int deleted = usersRepository.deleteUser(id);
            // deleted = usersRepository.reviveUser(id);
            if(deleted == 0) throw new UserManagementException(user.user_name() + "の削除に失敗");
        }
    }

}
