package jp.ac.chitose.ir.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import jp.ac.chitose.ir.views.login.LoginView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        setLoginView(http, LoginView.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // デフォルトの設定を適用。要確認
        super.configure(web);
    }
    @Bean
    public UserDetailsManager userDetailsService() {
        // データベースからユーザーの情報を取ってくるメソッド
        // Login画面から遷移するため仮のアカウントを登録
        UserDetails user =
                User.withUsername("testuser")
                        .password("{noop}IR_prmn")
                        .roles("USER")
                        .build();
        return new InMemoryUserDetailsManager(user);
    }

}
