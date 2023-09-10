package com.wsb.wsbfinalproject2022.authority;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.repository.query.Param;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "persons", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class Person {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @NotBlank
    @Column
    private String username;

    @NotBlank
    @Email
    @Column
    private String email;

    @NotBlank
    @Column
    String password;

    @NotBlank
    @Column
    private String realName;


   @ManyToMany(cascade = CascadeType.MERGE)
   @JoinTable(name = "person_role", joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
   private Set<Role> roles;


    public Person(String email, String password, String realName) {
        this.email = email;
        this.password = password;
        this.realName = realName;
    }

    private Person(String username, String email, String password, String realName, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.realName = realName;
        this.roles = roles;
    }

    public static Person of(String username, String email, String password, String realName, Set<Role> userRoles) {
        return new Person(username, email, password, realName, userRoles);
    }
    public Person() {

    }


}
