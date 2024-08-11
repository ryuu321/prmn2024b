package jp.ac.chitose.ir.infrastructure.repository;


import jp.ac.chitose.ir.application.service.management.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<User> getUserInformation(String loginId, String password){
        List<User> userOp = jdbcClient.sql("""
                SELECT
                  A.id, A.login_id, A.user_name AS name, A.password, A.is_available, C.role_name AS role
                FROM
                  users A,
                  user_role B,
                  role C
                WHERE A.id = B.user_id
                AND B.role_id = C.id
                AND A.is_available
                AND A.login_id = ?
                AND A.password = ?
                """)
                .params(loginId, password)
                .query(new DataClassRowMapper<>(User.class))
                .list();
        return userOp;
    }
}