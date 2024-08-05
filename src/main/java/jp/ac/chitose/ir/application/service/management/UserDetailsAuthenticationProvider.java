package jp.ac.chitose.ir.application.service.management;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.List;

public class UserDetailsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {


    private AuthenticationService authenticationService;

    public UserDetailsAuthenticationProvider(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {

        String password = (String) authentication.getCredentials(); // authenticationからpasswordを取得

        List<User> loginUserList = authenticationService.authenticate(username, password);
        System.out.print("認証結果: ");
        if (loginUserList.isEmpty()) {
            System.out.println("認証に失敗しました. username=" + username);
            // 上手くいかなかったらかえる
            throw new BadCredentialsException("認証に失敗しました。");
        }
        System.out.println("認証に成功しました.username=" + username);

        User user = loginUserList.get(0);
        HashSet<String> roles = new HashSet<>();

        for(User u : loginUserList){
            roles.add(u.role());
        }

        LoginUser loginUser = new LoginUser(user, roles);
        System.out.println("isAdmin : " + loginUser.isAdmin());
        System.out.println("isTeacher : " + loginUser.isTeacher());
        System.out.println("isStudent : " + loginUser.isStudent());
        System.out.println("isCommission : " + loginUser.isCommission());

        return loginUser;
    }
}