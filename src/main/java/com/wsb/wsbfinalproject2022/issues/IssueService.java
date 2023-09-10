package com.wsb.wsbfinalproject2022.issues;

import com.wsb.wsbfinalproject2022.authority.Person;
import com.wsb.wsbfinalproject2022.authority.PersonRepository;
import com.wsb.wsbfinalproject2022.projects.Project;
import com.wsb.wsbfinalproject2022.projects.ProjectFilter;
import com.wsb.wsbfinalproject2022.projects.ProjectRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor(force = true)
public class IssueService {

    @Autowired
    private final ProjectRepository projectRepository;

    @Autowired
    private final PersonRepository personRepository;

    @Autowired
    private final IssueRepository issueRepository;

    public IssueService(ProjectRepository projectRepository, PersonRepository personRepository,IssueRepository issueRepository) {
        this.projectRepository = projectRepository;
        this.personRepository = personRepository;
        this.issueRepository = issueRepository;
    }


    public List<Issue> findAll() {
        return issueRepository.findAll();
    }


}

