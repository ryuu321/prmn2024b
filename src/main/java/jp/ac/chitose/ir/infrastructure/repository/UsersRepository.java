package jp.ac.chitose.ir.infrastructure.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class UsersRepository {
    // フィールド；JDBC Client
    @Autowired
    private JdbcClient jdbcClient;

    // コンストラクタ
    public UsersRepository(JdbcClient jdbcClient){
        this.jdbcClient = jdbcClient;
    }

    // login_id からユーザを取得する(既にいるか確認)
    public long getUsersCount(String loginId){
        Long usersCount = jdbcClient.sql("""
                SELECT count(*) FROM users WHERE login_id = ?
                """)
                .params(loginId)
                .query(Long.class)
                .single();
        return usersCount;
    }

    // ユーザ追加
    // returning id
    public long addUser(String loginId,String username, String password){
        // 日付の取得
        Timestamp createdAt = new Timestamp(System.currentTimeMillis());

        // user_idを取得するための変数
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // users テーブルへの追加
        int inserted = jdbcClient.sql("""
                        INSERT INTO users(login_id, user_name, password, is_available, created_at)
                        VALUES (?, ?, ?, TRUE, ?)
                        """)
                .params(loginId, username, password, createdAt)
                .update(keyHolder, "id");
        System.out.println("inserted : " + inserted);

        long userId = keyHolder.getKey().longValue();
        return userId;
    }

    // ロール追加
    public void addRole(long userId, int roleId){
        int inserted = jdbcClient.sql("""
                        INSERT INTO user_role(user_id, role_id) VALUES (?, ?)
                        """)
                .params(userId, roleId)
                .update();
        System.out.println("inserted : " + inserted);
    }

    // ユーザ情報変更
    public void updateUser(long id, String loginId, String username, String password){
        int updated = jdbcClient.sql("""
                UPDATE users
                SET login_id = ?, user_name = ?, password = ?
                WHERE id = ?
                """)
                .params(loginId, username, password, id)
                .update();
        System.out.println("updated : " + updated);
    }

    // ロールを全て削除
    // 全削除→チェックされたロールを追加 という形でロール変更に用いる
    public void deleteRoles(long userId){
        int deleted = jdbcClient.sql("""
                DELETE FROM user_role
                WHERE user_id = ?
                """)
                .param(userId)
                .update();
        System.out.println("deleted : " + deleted);
    }

    // パスワード変更
    public void updatePassword(long userId, String password){
        int updated = jdbcClient.sql("""
                UPDATE users
                SET password = ?
                WHERE id = ?
                """)
                .params(password, userId)
                .update();
        System.out.println("updated : " + updated);
    }

    // ユーザ削除(無効化)
    public int deleteUser(long userId){
        // 日付の取得
        Timestamp deletedAt = new Timestamp(System.currentTimeMillis());

        int deleted = jdbcClient.sql("""
                UPDATE users
                SET is_available = FALSE, deleted_at = ?
                WHERE id = ?
                """)
                .params(deletedAt, userId)
                .update();
        System.out.println("deleted : " + deleted);
        return deleted;
    }

    // 削除したユーザを有効化
    public int reviveUser(long userId){
        int deleted = jdbcClient.sql("""
                UPDATE users
                SET is_available = TRUE, deleted_at = NULL
                WHERE id = ?
                """)
                .params(userId)
                .update();
        System.out.println("deleted : " + deleted);
        return deleted;
    }

    // テスト用に使うかもしれないので一応作った痕跡を残さない削除 後々消す
    public int deleteData(long id){
        this.deleteRoles(id);
        int deleted = jdbcClient.sql("""
                DELETE FROM users
                WHERE id = ?
                """)
                .param(id)
                .update();
        System.out.println("deleted : " + deleted);
        return deleted;
    }

}
