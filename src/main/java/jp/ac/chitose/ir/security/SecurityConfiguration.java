package jp.ac.chitose.ir.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import jp.ac.chitose.ir.views.login.LoginView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {

    @Autowired
    private AuthenticationService authenticationService;

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
    public AuthenticationProvider getAuthenticationProvider() {
        // 認証状態とユーザーの情報を取得
        return new UserDetailsAuthenticationProvider(authenticationService);
    }

}
