package jp.ac.chitose.ir.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

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
    public Optional<User> getUserInformation(String name,String password){
        Optional<User> userOp = jdbcClient.sql("""
                SELECT *
                FROM users
                WHERE user_name = ? AND password = ?
                """)
                .params(name, password)
                .query(new DataClassRowMapper<>(User.class))
                .optional();
        return userOp;
    }
}