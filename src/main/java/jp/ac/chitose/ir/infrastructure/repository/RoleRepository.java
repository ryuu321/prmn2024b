package jp.ac.chitose.ir.infrastructure.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleRepository {
    // フィールド；JDBC Client
    @Autowired
    private JdbcClient jdbcClient;

    // コンストラクタ
    public RoleRepository(JdbcClient jdbcClient){
        this.jdbcClient = jdbcClient;
    }

    // roleの名前(display_name?)からrole_idを取得
    public Optional<Integer> getRoleId(String roleName){
        Optional<Integer> roleIdOp = jdbcClient.sql("""
                SELECT id FROM role WHERE display_name = ?
                """)
                .params(roleName)
                .query(Integer.class)
                .optional();
        return roleIdOp;
    }
}
