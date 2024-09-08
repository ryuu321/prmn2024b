package jp.ac.chitose.ir.infrastructure.repository;


import jp.ac.chitose.ir.application.service.management.Password;
import jp.ac.chitose.ir.application.service.management.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AuthenticationRepository {

    // フィールド；JDBC Client
    @Autowired
    private JdbcClient jdbcClient;

    // コンストラクタ
    // JDBC Clientの実体を受け取れるように
    // @Autowiredは動かなそうならつける
    public AuthenticationRepository(JdbcClient jdbcClient){
        this.jdbcClient = jdbcClient;
    }

    // JDBC Clientを使ったデータ取得のメソッド
    public Optional<User> getUserInformation(String loginId, String password){
        Optional<User> userOp = jdbcClient.sql("""
                SELECT id, login_id, user_name AS name, is_available
                FROM users
                WHERE is_available
                AND login_id = ?
                AND password = ?
                """)
                .params(loginId, password)
                .query(new DataClassRowMapper<>(User.class))
                .optional();
        return userOp;
    }

    public Optional<User> getUserInformation(String loginId){
        Optional<User> userOp = jdbcClient.sql("""
                SELECT id, login_id, user_name AS name, is_available
                FROM users
                WHERE is_available
                AND login_id = ?
                """)
                .params(loginId)
                .query(new DataClassRowMapper<>(User.class))
                .optional();
        return userOp;
    }

    public Optional<Password> getPassword(String loginId){
        Optional<Password> passwordOp = jdbcClient.sql("""
                SELECT password AS value
                FROM users
                WHERE login_id = ?
                """)
                .params(loginId)
                .query(new DataClassRowMapper<>(Password.class))
                .optional();
        return passwordOp;
    }

    public Optional<Password> getPassword(long userId){
        Optional<Password> passwordOp = jdbcClient.sql("""
                SELECT password AS value
                FROM users
                WHERE id = ?
                """)
                .params(userId)
                .query(new DataClassRowMapper<>(Password.class))
                .optional();
        return passwordOp;
    }
}