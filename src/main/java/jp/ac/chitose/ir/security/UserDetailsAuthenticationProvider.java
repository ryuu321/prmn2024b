package jp.ac.chitose.ir.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class UserDetailsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {


    private AuthenticationService authenticationService;

    public UserDetailsAuthenticationProvider(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {}

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {

        String password = (String) authentication.getCredentials(); // authenticationからpasswordを取得

        Optional<User> optionalLoginUser = authenticationService.authenticate(username, password);
        System.out.println("認証結果=" + optionalLoginUser.isPresent());
//
        if ( optionalLoginUser.isEmpty() ) {
            System.out.println("認証に失敗しました. username=" + username);
            throw new BadCredentialsException("認証に失敗しました。");
        }


        // roleを取得する処理

        //LoginUser loginUser = new LoginUser(account, role);
        //return loginUser;
        return null;
    }
}