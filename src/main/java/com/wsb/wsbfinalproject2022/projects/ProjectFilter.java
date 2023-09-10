package com.wsb.wsbfinalproject2022.projects;

import com.wsb.wsbfinalproject2022.authority.Person;
import com.wsb.wsbfinalproject2022.projects.Project;
import groovyjarjarpicocli.CommandLine;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectFilter {

   private String name;
   private String creator;
   private String globalSearch;


    public Specification<Project> buildQuery() {
        return Specification.anyOf(
                ilike("name", globalSearch),
                ilike("description", globalSearch),
                ilike("code", globalSearch)
        ).and(
         Specification.allOf(
                ilike("name",name)));
    }
    private Specification<Project> ilike(String property, String value){
        String valueToCompare = value == null ?"" : value;
        return (root, query, builder) -> builder.like(builder.lower(root.get(property)), "%" + valueToCompare.toLowerCase() + "%");
    }

}
