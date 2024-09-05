package jp.ac.chitose.ir.infrastructure.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class UsersRepository {
    // フィールド；JDBC Client
    @Autowired
    private JdbcClient jdbcClient;
    private final DateTimeFormatter dateTimeFormatter;
    // とりあえずここに書いたけどserviceに書いた方がよさそう
    private final String defaultPassword;
    // 場合によっては消す

    // コンストラクタ
    public UsersRepository(JdbcClient jdbcClient){
        this.jdbcClient = jdbcClient;
        // yyyy-MM-ddの方が良いかも
        // 両方違ったら後で考える
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        // めちゃくちゃ仮
        defaultPassword = "password";
    }

    // ユーザ追加
    // デフォルトのパスワードを固定にするかusernameに合わせるかなどの確認
    public void addUser(String loginId,String username){
        //日付の取得・yyyy/MM/dd の形にフォーマット
        LocalDateTime date = LocalDateTime.now();
        String formatDate = dateTimeFormatter.format(date);
        //Timestamp date = new Timestamp(System.currentTimeMillis());

        // users テーブルへの追加
        int inserted = jdbcClient.sql("""
                        INSERT into users(login_id, user_name, password, is_available, created_at) 
                        values (?, ?, ?, TRUE, ?)
                        """)
                .params(loginId, username, defaultPassword, formatDate)
                .update();
        System.out.println("inserted : " + inserted);
    }

    // ロール追加
    public void addRole(long userId, int roleId){
        int inserted = jdbcClient.sql("""
                        INSERT INTO user_role VALUES (?, ?)
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
                .query(new DataClassRowMapper<>(Integer.class))
                .single();
        return id;
    }

    // ユーザ情報変更
    // 変更内容どう受け取ってくるか要検討
    // serviceでfor文？sql文を順々に結合？->一旦後者で実装
    public void updateUser(String loginId, List<String> columns, List<String> params){
        StringBuilder sql = new StringBuilder("update users SET ");
        for(int i=0;i<columns.size();i++){
            if(i>0) sql.append(", ");
            sql.append(columns.get(i) + " = ?");
        }
        sql.append(" WHERE login_id = ?");
        int updated = jdbcClient.sql(sql.toString())
                .params(params)
                .param(loginId)
                .update();
        System.out.println("updated : " + updated);
    }

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
    public int deleteUser(String loginId, String username, LocalDateTime deleteAt){
        String formatDate = dateTimeFormatter.format(deleteAt);
        int deleted = jdbcClient.sql("""
                update users 
                SET is_available = FALSE, deleted_at = ?
                WHERE login_id = ?
                AND user_name = ?
                """)
                .params(formatDate, loginId, username)
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
