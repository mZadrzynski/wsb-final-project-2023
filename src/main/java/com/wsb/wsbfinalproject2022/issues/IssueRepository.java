package com.wsb.wsbfinalproject2022.issues;

import com.wsb.wsbfinalproject2022.authority.Person;
import com.wsb.wsbfinalproject2022.projects.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long>{


}
