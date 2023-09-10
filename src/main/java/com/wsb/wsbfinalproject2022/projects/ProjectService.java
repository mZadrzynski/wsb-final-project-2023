package com.wsb.wsbfinalproject2022.projects;

import com.wsb.wsbfinalproject2022.authority.Person;
import com.wsb.wsbfinalproject2022.authority.PersonRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor(force = true)
public class ProjectService {

    @Autowired
    private final ProjectRepository projectRepository;

    @Autowired
    private final PersonRepository personRepository;

    public ProjectService(ProjectRepository projectRepository, PersonRepository personRepository) {
        this.projectRepository = projectRepository;
        this.personRepository = personRepository;
    }

    public List<Project> findAll(ProjectFilter filter) {
        return projectRepository.findAll(filter.buildQuery());
    }



  public List<Person> findAll() {
      return personRepository.findAll();
    }


}
