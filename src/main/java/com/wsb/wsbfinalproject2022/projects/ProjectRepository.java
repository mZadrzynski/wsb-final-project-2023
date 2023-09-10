package com.wsb.wsbfinalproject2022.projects;

import com.wsb.wsbfinalproject2022.authority.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

    List<Project> findAllByPerson(Person person);
}
