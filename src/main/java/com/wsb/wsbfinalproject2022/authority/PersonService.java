package com.wsb.wsbfinalproject2022.authority;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final AuthorityRepository authorityRepository;
    private final PersonRepository personRepository;
    public PersonService(AuthorityRepository authorityRepository, PersonRepository personRepository) {
        this.authorityRepository = authorityRepository;
        this.personRepository = personRepository;
    }
    public void prepareAdminUser() { // TODO: Nie twórzmy użytkownika, jeśli już taki istnieje
// TODO: Login i hasło weźmy ze zmiennej
        Person person = new Person("admin", "123456", "Administrator");
        List<Authority> authorities = authorityRepository.findAll();
        person.setAuthorities(new HashSet<>(authorities));
        savePerson(person);
    }
    private void savePerson(Person person) { personRepository.save(person); } // TODO: Hash hasła
}