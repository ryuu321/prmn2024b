package jp.ac.chitose.ir.infrastructure.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
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
                        INSERT into users(login_id, user_name, password, is_available, created_at) 
                        values (?, ?, ?, TRUE, ?)
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

    // ユーザidの取得(主にユーザ追加で使用)
    public long getUserId(String loginId){
        long id = jdbcClient.sql("""
                SELECT id FROM users WHERE login_id = ?
                """)
                .params(loginId)
                .query(new DataClassRowMapper<>(Long.class))
                .single();
        return id;
    }

    // ユーザ情報変更
    // 変更内容どう受け取ってくるか要検討
    // serviceでfor文？sql文を順々に結合？->一旦後者で実装
    public void updateUser(long id, String loginId, String username, String password){
        int updated = jdbcClient.sql("""
                update users
                SET login_id = ?, user_name = ?, password = ?
                WHERE id = ?
                """)
                .params(loginId, username, password, id)
                .update();
        System.out.println("updated : " + updated);
    }

    // パスワード変更
    public void updatePassword(String loginId, String password){
        int updated = jdbcClient.sql("""
                update users 
                SET password = ?
                WHERE login_id = ?
                """)
                .params(password, loginId)
                .update();
        System.out.println("updated : " + updated);
    }

    // ユーザ削除(無効化)
    public int deleteUser(long userId){
        // 日付の取得
        Timestamp deletedAt = new Timestamp(System.currentTimeMillis());

        int deleted = jdbcClient.sql("""
                update users
                SET is_available = FALSE, deleted_at = ?
                WHERE id = ?
                """)
                .params(deletedAt, userId)
                .update();
        System.out.println("deleted : " + deleted);
        return deleted;
    }

    // テスト用に使うかもしれないので一応作った痕跡を残さない削除 後々消す
    public void deleteData(long id){
        int deleted = jdbcClient.sql("""
                delete from users 
                WHERE id = ?
                """)
                .param(id)
                .update();
        System.out.println("deleted : " + deleted);
    }

}
