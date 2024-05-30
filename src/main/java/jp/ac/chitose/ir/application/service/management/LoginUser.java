package jp.ac.chitose.ir.application.service.management;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class LoginUser implements UserDetails {

    private User user;

    private Collection<GrantedAuthority> authorities;

    public LoginUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }

        GrantedAuthority authority = new SimpleGrantedAuthority(user.role());
        authorities = new ArrayList<>();
        authorities.add(authority);
        return authorities;
    }

    @Override
    public String getUsername() {
        return user.name();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return "";
    }

    public long getAccountId(){
        return user.id();
    }

    public boolean isTeacher() {
        return user.role().equals("TEACHER");
    }

    public boolean isStudent() {
        return user.role().equals("STUDENT");
    }

    public boolean isCommission() { return user.role().equals("COMMISSION"); }

    public boolean isAdmin() { return user.role().equals("ADMIN"); }

}
