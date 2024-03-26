package jp.ac.chitose.ir.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AuthenticationRepository {

    // フィールド；JDBC Client
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // コンストラクタ
    // JDBC Clientの実体を受け取れるように
    // @Autowiredは動かなそうならつける
    //public AuthenticationRepository(DataSource dataSource){
    //    this.jdbcClient = jdbcClient;
    //}

    // JDBC Clientを使ったデータ取得のメソッド
    public Optional<User> getUserInformation(String name, String password){

        String sql = """
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
                """;
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(sql, new DataClassRowMapper<>(User.class), name, password);
        }catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(user);

        /* Optional<User> userOp = jdbcClient.sql("""
                SELECT *
                FROM users
                WHERE user_name = ? AND password = ?
                """)
                .params(name, password)
                .query(new DataClassRowMapper<>(User.class))
                .optional();
        return userOp;
        */
    }
}