package jp.ac.chitose.ir.infrastructure.repository;

import org.springframework.beans.factory.annotation.Autowired;
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
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        // めちゃくちゃ仮
        defaultPassword = "password";
    }

    // ユーザ追加
    // もし同じユーザアカウントに複数ロールを振る可能性があるなら変更必須
    // デフォルトのパスワードを固定にするかusernameに合わせるかなどの確認
    public void addUser(int user_id, String username, int role_id){
        //日付の取得・yyyy/MM/dd の形にフォーマット
        LocalDateTime date = LocalDateTime.now();
        String formatDate = dateTimeFormatter.format(date);
        //Timestamp date = new Timestamp(System.currentTimeMillis());

        // users テーブルへの追加
        int inserted = jdbcClient.sql("""
                        INSERT into users(user_name, password, is_available, created_at) 
                        values (?, ?, TRUE, ?)
                        """)
                .params(username, defaultPassword, formatDate)
                .update();
        System.out.println("inserted : " + inserted);

        // user_role テーブルへの追加
        // roleを一括で取ってくる前処理をserviceに入れるならfor文？
        inserted = jdbcClient.sql("""
                        INSERT into user_role values (?, ?)
                        """)
                .params(user_id, role_id)
                .update();
        System.out.println("inserted : " + inserted);
    }

    // ユーザ情報変更
    // 変更内容どう受け取ってくるか要検討
    // serviceでfor文？sql文を順々に結合？->一旦後者で実装
    public void updateUser(int id, List<String> columns, List<String> params){
        StringBuilder sql = new StringBuilder("update users SET ");
        for(int i=0;i<columns.size();i++){
            if(i>0) sql.append(", ");
            sql.append(columns.get(i) + " = ?");
        }
        sql.append(" WHERE id = ?");
        int updated = jdbcClient.sql(sql.toString())
                .params(params)
                .param(id)
                .update();
        System.out.println("updated : " + updated);
    }

    // ユーザ削除(無効化)
    public void deleteUser(int id){
        LocalDateTime date = LocalDateTime.now();
        String formatDate = dateTimeFormatter.format(date);
        int deleted = jdbcClient.sql("""
                update users 
                SET is_available = FALSE, deleted_at = ?
                WHERE id = ?
                """)
                .params(formatDate, id)
                .update();
        System.out.println("deleted : " + deleted);
    }

    // テスト用に使うかもしれないので一応作った痕跡を残さない削除 後々消す
    public void deleteData(int id){
        int deleted = jdbcClient.sql("""
                delete from users  
                WHERE id = ?
                """)
                .param(id)
                .update();
        System.out.println("deleted : " + deleted);
    }

}
