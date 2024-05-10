package jp.ac.chitose.ir.security;


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
    public Optional<User> getUserInformation(String name, String password){
        Optional<User> userOp = jdbcClient.sql("""
                SELECT
                  A.id, A.user_name AS name, A.password, A.is_available, C.role_name AS role
                FROM
                  users A,
                  user_role B,
                  role C
                WHERE A.id = B.user_id
                AND B.role_id = C.id
                AND A.is_available
                AND A.user_name = ?
                AND A.password = ?
                """)
                .params(name, password)
                .query(new DataClassRowMapper<>(User.class))
                .optional();
        return userOp;
    }
}