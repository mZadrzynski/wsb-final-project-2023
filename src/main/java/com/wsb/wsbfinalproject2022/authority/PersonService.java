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

    private void savePerson(Person person) { personRepository.save(person); } // TODO: Hash hasła
}