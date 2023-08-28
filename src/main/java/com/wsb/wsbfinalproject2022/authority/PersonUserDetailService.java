package com.wsb.wsbfinalproject2022.authority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PersonUserDetailService implements UserDetailsService {

    @Autowired
    private PersonRepository repository;
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Person> person = repository.findByLogin(login);
        return person.map(PersonUserDetails::new)
                .orElseThrow(()->new UsernameNotFoundException("user not found" + login));
    }
}
