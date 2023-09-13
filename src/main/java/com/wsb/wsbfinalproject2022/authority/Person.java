package com.wsb.wsbfinalproject2022.authority;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
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
    @Size(min = 3,max = 20)
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


    public Person() {

    }


}
