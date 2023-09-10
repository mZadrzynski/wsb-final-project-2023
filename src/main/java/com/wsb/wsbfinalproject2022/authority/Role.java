package com.wsb.wsbfinalproject2022.authority;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false,unique = true)
    private RoleType name;


    public Role(RoleType name){
        this.name = name;
    }

    public Role() {

    }
    public RoleType getName() {
        return name;
    }

    public void setName(RoleType name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
