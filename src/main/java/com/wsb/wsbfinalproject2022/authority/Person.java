package com.wsb.wsbfinalproject2022.authority;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String pass;

    @Column(nullable = false)
    private String userRealName;


    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "person_authorities",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    Set<Authority> authorities;

}
