package com.wsb.wsbfinalproject2022.authority;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    private BCryptPasswordEncoder bCryptPasswordEncoder;

    protected void savePerson(Person person) {
        String hashedPassword = bCryptPasswordEncoder.encode(person.password);
        person.setPassword(hashedPassword);

        personRepository.save(person);
    }

}

