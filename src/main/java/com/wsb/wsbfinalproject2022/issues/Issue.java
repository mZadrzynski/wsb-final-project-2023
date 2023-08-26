package com.wsb.wsbfinalproject2022.issues;

import com.wsb.wsbfinalproject2022.projects.Project;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Issue {

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    private String code;
    private String title;
    private String content;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IssueType type = IssueType.TASK;
    @Id
    @GeneratedValue
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
