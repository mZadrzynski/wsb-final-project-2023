package com.wsb.wsbfinalproject2022.issues;


import com.wsb.wsbfinalproject2022.authority.Person;
import com.wsb.wsbfinalproject2022.projects.Project;
import com.wsb.wsbfinalproject2022.projects.ProjectService;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueFilter {


    public Project project;
    public String status;
    public Person assignee;

    public Specification<Issue> buildQuery() {
        return
                Specification.allOf(
                        ilike("status", status),
                        equalTo("assignee", assignee),
                        equalTo("project", project));
    }
    private Specification<Issue> ilike(String property, String value){
        String valueToCompare = value == null ?"" : value;
        return (root, query, builder) -> builder.like(builder.lower(root.get(property)), "%" + valueToCompare.toLowerCase() + "%");
    }

    private Specification<Issue> equalTo(String property, Object value) {
        if (value == null) {
            return Specification.where(null);
        }

        return (root, query, builder) -> builder.equal(root.get(property), value);
    }


}
