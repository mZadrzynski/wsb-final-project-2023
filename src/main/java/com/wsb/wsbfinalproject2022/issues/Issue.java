package com.wsb.wsbfinalproject2022.issues;

import com.wsb.wsbfinalproject2022.authority.Person;
import com.wsb.wsbfinalproject2022.projects.Project;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Entity
public class Issue {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Size(min = 3)
    private String code;

    @NotNull
    @Size(min= 5)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IssueType type = IssueType.TASK;

    @Enumerated(EnumType.STRING)
    private IssueStatus status = IssueStatus.OPEN;

    @Enumerated(EnumType.STRING)
    private IssuePriority priority = IssuePriority.MEDIUM;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private Person person;

    @ManyToOne
    @JoinColumn(name = "assignee_id", nullable = false)
    private Person assignee;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private Date dateCreated;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
