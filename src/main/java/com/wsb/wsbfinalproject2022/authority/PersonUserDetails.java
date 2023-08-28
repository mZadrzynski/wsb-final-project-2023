package com.wsb.wsbfinalproject2022.authority;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class PersonUserDetails implements UserDetails {

    private Long id;
    private String login;
    private String pass;
    private String userRealName;

    private Set<Authority> authorities;



    public PersonUserDetails(Person person) {
        id = person.getId();
        login = person.getLogin();
        pass = person.getPass();
        userRealName = person.getUserRealName();
        authorities = person.getAuthorities();
    }

    public PersonUserDetails(Long id, String login, String pass, String userRealName) {
        this.id = id;
        this.login = login;
        this.pass = pass;
        this.userRealName = userRealName;

    }
    public PersonUserDetails(Set<Authority> authorities) {
        this.authorities = authorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return pass;
    }

    @Override
    public String getUsername() {
        return login;
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
}
